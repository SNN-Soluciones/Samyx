package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.IEmisorActividadesDao;
import snn.soluciones.com.models.entity.EmisorActividades;
import snn.soluciones.com.models.entity.Emisor;
import snn.soluciones.com.service.interfaces.IEmisorActividadesService;
import java.util.List;
import java.util.ArrayList;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmisorActividadesServiceImpl implements IEmisorActividadesService {

  @Autowired
  private IEmisorActividadesDao _dao;

  @Autowired
  private RestTemplate restTemplate;

  private static final Logger log = LoggerFactory.getLogger(EmisorActividadesServiceImpl.class);
  private static final String HACIENDA_API_BASE = "https://api.hacienda.go.cr";

  // MÉTODOS EXISTENTES

  @Override
  public List<EmisorActividades> findAllByEmisorId(Long emisorId) {
    return this._dao.findAllByEmisorId(emisorId);
  }

  @Override
  @Transactional
  public void deleteByIdAndEmisorId(Long id, Long emisorId) {
    this._dao.deleteByIdAndEmisorId(id, emisorId);
  }

  @Override
  @Transactional
  public void save(EmisorActividades entity) {
    this._dao.save(entity);
  }

  // NUEVOS MÉTODOS - SINCRONIZACIÓN DESDE HACIENDA

  @Override
  @Transactional
  public List<EmisorActividades> sincronizarActividadesDesdeHacienda(Emisor emisor) throws Exception {

    if (emisor == null || emisor.getIdentificacion() == null) {
      throw new IllegalArgumentException("El emisor y su identificación son requeridos");
    }

    List<EmisorActividades> actividadesGuardadas = new ArrayList<>();

    try {
      // Consultar al API de Hacienda
      String url = HACIENDA_API_BASE + "/fe/ae?identificacion=" + emisor.getIdentificacion();

      HttpHeaders headers = new HttpHeaders();
      headers.set("User-Agent", "NathBit-POS/1.0");
      HttpEntity<String> entity = new HttpEntity<>(headers);

      log.info("Consultando actividades en Hacienda para: " + emisor.getIdentificacion());

      ResponseEntity<Object> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          Object.class
      );

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

        // Limpiar actividades previas
        eliminarActividadesDelEmisor(emisor.getId());
        log.info("Actividades previas del emisor eliminadas");

        // Procesar la respuesta
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseData = objectMapper.convertValue(response.getBody(), JsonNode.class);

        if (responseData.isArray()) {
          // Si es un array
          for (JsonNode actividad : responseData) {
            EmisorActividades ea = construirEmisorActividad(emisor, actividad);
            if (ea != null) {
              this._dao.save(ea);
              actividadesGuardadas.add(ea);
              log.debug("Actividad guardada: " + ea.getCodigoActividadEmisor());
            }
          }
        } else if (responseData.isObject()) {
          // Si es un objeto único
          EmisorActividades ea = construirEmisorActividad(emisor, responseData);
          if (ea != null) {
            this._dao.save(ea);
            actividadesGuardadas.add(ea);
            log.debug("Actividad guardada: " + ea.getCodigoActividadEmisor());
          }
        }

        log.info("Sincronización completada. Actividades guardadas: " + actividadesGuardadas.size());
      }

      return actividadesGuardadas;

    } catch (Exception e) {
      log.error("Error al sincronizar actividades desde Hacienda: " + e.getMessage(), e);
      throw e;
    }
  }

  /**
   * Construye una entidad EmisorActividades desde un nodo JSON
   */
  private EmisorActividades construirEmisorActividad(Emisor emisor, JsonNode nodo) {

    // Extraer código (puede estar en diferentes campos)
    String codigo = null;
    if (nodo.has("codigo")) {
      codigo = nodo.get("codigo").asText();
    } else if (nodo.has("code")) {
      codigo = nodo.get("code").asText();
    } else if (nodo.has("codigoActividad")) {
      codigo = nodo.get("codigoActividad").asText();
    }

    // Extraer descripción
    String descripcion = null;
    if (nodo.has("descripcion")) {
      descripcion = nodo.get("descripcion").asText();
    } else if (nodo.has("descripcionActividad")) {
      descripcion = nodo.get("descripcionActividad").asText();
    } else if (nodo.has("nombre")) {
      descripcion = nodo.get("nombre").asText();
    }

    // Validar que tengamos al menos el código
    if (codigo == null || codigo.isEmpty()) {
      log.warn("Actividad sin código identificado, será ignorada");
      return null;
    }

    EmisorActividades ea = new EmisorActividades();
    ea.setEmisor(emisor);
    ea.setCodigoActividadEmisor(codigo.trim());
    ea.setDetalleActividad(descripcion != null ? descripcion.trim() : codigo);
    ea.setEstado("A");

    return ea;
  }

  @Override
  @Transactional
  public void eliminarActividadesDelEmisor(Long emisorId) {
    List<EmisorActividades> actividades = this._dao.findAllByEmisorId(emisorId);
    for (EmisorActividades ea : actividades) {
      this._dao.delete(ea);
    }
    log.info("Actividades eliminadas para emisor: " + emisorId);
  }

  @Override
  public long contarActividadesDelEmisor(Long emisorId) {
    return this._dao.findAllByEmisorId(emisorId).size();
  }
}
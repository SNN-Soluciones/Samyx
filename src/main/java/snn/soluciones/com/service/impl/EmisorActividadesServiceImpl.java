package snn.soluciones.com.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snn.soluciones.com.dto.GometaActividadDto;
import snn.soluciones.com.models.dao.IEmisorActividadesDao;
import snn.soluciones.com.models.entity.EmisorActividades;
import snn.soluciones.com.models.entity.Emisor;
import snn.soluciones.com.service.interfaces.IEmisorActividadesService;
import snn.soluciones.com.service.interfaces.IGometaService;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmisorActividadesServiceImpl implements IEmisorActividadesService {

  @Autowired
  private IEmisorActividadesDao emisorActividadesDao;

  @Autowired
  private IGometaService gometaService;

  private static final Logger log = LoggerFactory.getLogger(EmisorActividadesServiceImpl.class);

  // ==================== MÉTODOS EXISTENTES ====================

  @Override
  public List<EmisorActividades> findAllByEmisorId(Long emisorId) {
    return this.emisorActividadesDao.findAllByEmisorId(emisorId);
  }

  @Override
  @Transactional
  public void deleteByIdAndEmisorId(Long id, Long emisorId) {
    this.emisorActividadesDao.deleteByIdAndEmisorId(id, emisorId);
  }

  @Override
  @Transactional
  public void save(EmisorActividades entity) {
    this.emisorActividadesDao.save(entity);
  }

  // ==================== NUEVOS MÉTODOS - GOMETA DGT 4.4 ====================

  /**
   * Sincroniza las actividades económicas desde la API Gometa (DGT 4.4)
   *
   * @param emisor El emisor para el cual se sincronizarán las actividades
   * @return Lista de actividades guardadas
   * @throws Exception En caso de error durante la sincronización
   */
  @Override
  @Transactional
  public List<EmisorActividades> sincronizarActividadesDesdeGometa(Emisor emisor) throws Exception {

    if (emisor == null || emisor.getIdentificacion() == null) {
      throw new IllegalArgumentException("El emisor y su identificación son requeridos");
    }

    List<EmisorActividades> actividadesGuardadas = new ArrayList<>();

    try {
      log.info("Iniciando sincronización de actividades desde Gometa para: {}",
          emisor.getIdentificacion());

      // Consultar actividades en Gometa
      List<GometaActividadDto> actividadesGometa = gometaService.obtenerActividadesPorIdentificacion(
          emisor.getIdentificacion()
      );

      if (actividadesGometa.isEmpty()) {
        log.warn("No se encontraron actividades en Gometa para: {}",
            emisor.getIdentificacion());
        return actividadesGuardadas;
      }

      // Limpiar actividades previas del emisor
      eliminarActividadesDelEmisor(emisor.getId());
      log.info("Actividades previas del emisor eliminadas");

      // Procesar y guardar nuevas actividades
      for (GometaActividadDto actividadGometa : actividadesGometa) {
        EmisorActividades ea = construirEmisorActividad(emisor, actividadGometa);
        if (ea != null) {
          this.emisorActividadesDao.save(ea);
          actividadesGuardadas.add(ea);
          log.debug("Actividad guardada: código={}, descripción={}",
              ea.getCodigoActividadEmisor(),
              ea.getDetalleActividad());
        }
      }

      log.info("Sincronización completada. Total de actividades guardadas: {}",
          actividadesGuardadas.size());

      return actividadesGuardadas;

    } catch (IllegalArgumentException e) {
      log.error("Error de validación: {}", e.getMessage());
      throw e;
    } catch (Exception e) {
      log.error("Error al sincronizar actividades desde Gometa: {}", e.getMessage(), e);
      throw new Exception("Error sincronizando actividades: " + e.getMessage(), e);
    }
  }

  /**
   * Sincroniza un catálogo completo de actividades económicas desde Gometa
   * Útil para mantener actualizado el catálogo de referencia
   *
   * @return Lista de todas las actividades disponibles
   * @throws Exception En caso de error
   */
  @Override
  @Transactional
  public List<GometaActividadDto> sincronizarCatalogoCompleto() throws Exception {

    try {
      log.info("Iniciando sincronización del catálogo completo de actividades desde Gometa");

      List<GometaActividadDto> actividades = gometaService.obtenerTodosLosCodigosActividades();

      log.info("Catálogo sincronizado. Total de actividades: {}", actividades.size());

      return actividades;

    } catch (Exception e) {
      log.error("Error sincronizando catálogo completo: {}", e.getMessage(), e);
      throw new Exception("Error sincronizando catálogo: " + e.getMessage(), e);
    }
  }

  /**
   * Valida que un código de actividad sea válido en Gometa
   *
   * @param codigoActividad El código a validar
   * @return true si el código es válido, false en caso contrario
   * @throws Exception En caso de error
   */
  @Override
  public boolean validarCodigoActividad(String codigoActividad) throws Exception {

    if (codigoActividad == null || codigoActividad.trim().isEmpty()) {
      return false;
    }

    try {
      return gometaService.validarCodigoActividad(codigoActividad);
    } catch (Exception e) {
      log.error("Error validando código de actividad: {}", e.getMessage());
      throw e;
    }
  }

  /**
   * Construye una entidad EmisorActividades desde un DTO de Gometa
   *
   * @param emisor El emisor propietario de la actividad
   * @param actividadGometa DTO con los datos de Gometa
   * @return Entidad EmisorActividades lista para persistir, o null si hay error
   */
  private EmisorActividades construirEmisorActividad(Emisor emisor, GometaActividadDto actividadGometa) {

    if (actividadGometa == null ||
        actividadGometa.getCodigo() == null ||
        actividadGometa.getCodigo().trim().isEmpty()) {

      log.warn("DTO de Gometa inválido o sin código");
      return null;
    }

    try {
      EmisorActividades ea = new EmisorActividades();
      ea.setEmisor(emisor);
      ea.setCodigoActividadEmisor(actividadGometa.getCodigo().trim());
      ea.setDetalleActividad(
          actividadGometa.getDescripcion() != null &&
              !actividadGometa.getDescripcion().trim().isEmpty()
              ? actividadGometa.getDescripcion().trim()
              : actividadGometa.getCodigo()
      );
      ea.setEstado("A"); // Estado activo por defecto

      return ea;

    } catch (Exception e) {
      log.error("Error construyendo EmisorActividades: {}", e.getMessage());
      return null;
    }
  }

  @Override
  @Transactional
  public void eliminarActividadesDelEmisor(Long emisorId) {

    try {
      List<EmisorActividades> actividades = this.emisorActividadesDao.findAllByEmisorId(emisorId);

      if (!actividades.isEmpty()) {
        this.emisorActividadesDao.deleteAll(actividades);
        log.info("Se eliminaron {} actividades del emisor: {}", actividades.size(), emisorId);
      }

    } catch (Exception e) {
      log.error("Error eliminando actividades del emisor {}: {}", emisorId, e.getMessage());
      throw e;
    }
  }

  @Override
  public long contarActividadesDelEmisor(Long emisorId) {
    return this.emisorActividadesDao.findAllByEmisorId(emisorId).size();
  }

  /**
   * Método de compatibilidad con la versión anterior (Hacienda)
   * Redirige a la nueva sincronización con Gometa
   */
  @Override
  @Transactional
  public List<EmisorActividades> sincronizarActividadesDesdeHacienda(Emisor emisor) throws Exception {
    log.warn("Método sincronizarActividadesDesdeHacienda está deprecado. Usando Gometa en su lugar.");
    return sincronizarActividadesDesdeGometa(emisor);
  }
}
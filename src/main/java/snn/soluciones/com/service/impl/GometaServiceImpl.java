package snn.soluciones.com.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import snn.soluciones.com.dto.GometaActividadDto;
import snn.soluciones.com.service.interfaces.IGometaService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GometaServiceImpl implements IGometaService {

    private static final Logger log = LoggerFactory.getLogger(GometaServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.gometa.base-url:https://api.gometa.cr/v1}")
    private String gometaBaseUrl;

    @Value("${api.gometa.key:}")
    private String gometaApiKey;

    @Value("${api.gometa.timeout:10000}")
    private int timeoutMs;

    @Override
    public List<GometaActividadDto> obtenerActividadesPorIdentificacion(String identificacion) throws Exception {
        
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es requerida");
        }

        List<GometaActividadDto> actividades = new ArrayList<>();

        try {
            String url = gometaBaseUrl + "/actividades?identificacion=" + identificacion.trim();
            
            log.info("Consultando actividades en Gometa para identificación: {}", identificacion);
            log.debug("URL: {}", url);

            HttpHeaders headers = buildHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                actividades = parseResponseBody(response.getBody());
                log.info("Se obtuvieron {} actividades desde Gometa", actividades.size());
            } else {
                log.warn("Respuesta no satisfactoria de Gometa. Status: {}", response.getStatusCode());
            }

            return actividades;

        } catch (RestClientException e) {
            log.error("Error de conexión con Gometa: {}", e.getMessage(), e);
            throw new Exception("Error conectando con la API Gometa: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error al obtener actividades desde Gometa: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<GometaActividadDto> obtenerTodosLosCodigosActividades() throws Exception {
        
        List<GometaActividadDto> actividades = new ArrayList<>();

        try {
            String url = gometaBaseUrl + "/actividades";
            
            log.info("Consultando catálogo completo de actividades en Gometa");
            log.debug("URL: {}", url);

            HttpHeaders headers = buildHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                actividades = parseResponseBody(response.getBody());
                log.info("Se obtuvieron {} actividades del catálogo", actividades.size());
            } else {
                log.warn("Respuesta no satisfactoria de Gometa. Status: {}", response.getStatusCode());
            }

            return actividades;

        } catch (RestClientException e) {
            log.error("Error de conexión con Gometa: {}", e.getMessage(), e);
            throw new Exception("Error conectando con la API Gometa: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error al obtener catálogo de actividades: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean validarCodigoActividad(String codigo) throws Exception {
        
        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        try {
            String url = gometaBaseUrl + "/actividades/" + codigo.trim();
            
            log.debug("Validando código de actividad en Gometa: {}", codigo);

            HttpHeaders headers = buildHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return response.getStatusCode().is2xxSuccessful();

        } catch (RestClientException e) {
            log.warn("Error validando código de actividad: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Error inesperado validando código: {}", e.getMessage(), e);
            throw e;
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "NathBit-POS/4.4");
        headers.set("Content-Type", "application/json");
        
        if (gometaApiKey != null && !gometaApiKey.trim().isEmpty()) {
            headers.set("Authorization", "Bearer " + gometaApiKey);
        }
        
        return headers;
    }

    private List<GometaActividadDto> parseResponseBody(String responseBody) throws JsonProcessingException {
        
        List<GometaActividadDto> actividades = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);

            JsonNode dataNode = rootNode.isArray() ? rootNode : rootNode.get("data");

            if (dataNode != null && dataNode.isArray()) {
                for (JsonNode nodo : dataNode) {
                    GometaActividadDto actividad = mapearNodoADto(nodo);
                    if (actividad != null) {
                        actividades.add(actividad);
                    }
                }
            } else if (rootNode.isObject()) {
                GometaActividadDto actividad = mapearNodoADto(rootNode);
                if (actividad != null) {
                    actividades.add(actividad);
                }
            }

            log.debug("Se parsearon {} actividades del response", actividades.size());

        } catch (JsonProcessingException e) {
            log.error("Error parseando JSON de Gometa: {}", e.getMessage(), e);
            throw e;
        }

        return actividades;
    }

    private GometaActividadDto mapearNodoADto(JsonNode nodo) {
        
        try {
            String codigo = obtenerValorString(nodo, "codigo", "code", "codigoActividad");
            String descripcion = obtenerValorString(nodo, "descripcion", "nombre", "descripcionActividad");

            if (codigo == null || codigo.trim().isEmpty()) {
                log.warn("Actividad sin código identificado, será ignorada");
                return null;
            }

            return GometaActividadDto.builder()
                    .codigo(codigo.trim())
                    .descripcion(descripcion != null ? descripcion.trim() : codigo)
                    .estado(obtenerValorString(nodo, "estado", "activo"))
                    .activo(obtenerValorBoolean(nodo, "activo", "vigente"))
                    .build();

        } catch (Exception e) {
            log.warn("Error mapeando nodo JSON: {}", e.getMessage());
            return null;
        }
    }

    private String obtenerValorString(JsonNode nodo, String... keys) {
        for (String key : keys) {
            if (nodo.has(key) && !nodo.get(key).isNull()) {
                return nodo.get(key).asText();
            }
        }
        return null;
    }

    private Boolean obtenerValorBoolean(JsonNode nodo, String... keys) {
        for (String key : keys) {
            if (nodo.has(key) && !nodo.get(key).isNull()) {
                return nodo.get(key).asBoolean();
            }
        }
        return true;
    }
}
package snn.soluciones.com.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import snn.soluciones.com.dto.GometaActividadDto;
import snn.soluciones.com.models.entity.Emisor;
import snn.soluciones.com.models.entity.EmisorActividades;
import snn.soluciones.com.service.interfaces.IEmisorActividadesService;
import snn.soluciones.com.service.interfaces.IEmisorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v4.4/emisor-actividades")
public class EmisorActividadesApiController {

    private static final Logger log = LoggerFactory.getLogger(EmisorActividadesApiController.class);

    @Autowired
    private IEmisorActividadesService emisorActividadesService;

    @Autowired
    private IEmisorService emisorService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{emisorId}")
    public ResponseEntity<Map<String, Object>> obtenerActividadesPorEmisor(
        @PathVariable Long emisorId) {

        Map<String, Object> response = new HashMap<>();

        try {
            List<EmisorActividades> actividades = emisorActividadesService.findAllByEmisorId(emisorId);

            response.put("success", true);
            response.put("data", actividades);
            response.put("count", actividades.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error obteniendo actividades del emisor {}: {}", emisorId, e.getMessage());
            response.put("success", false);
            response.put("error", "Error obteniendo actividades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/{emisorId}/sincronizar")
    public ResponseEntity<Map<String, Object>> sincronizarActividadesGometa(
        @PathVariable Long emisorId) {

        Map<String, Object> response = new HashMap<>();

        try {
            Emisor emisor = emisorService.findById(emisorId);

            if (emisor == null) {
                response.put("success", false);
                response.put("error", "Emisor no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            log.info("Iniciando sincronización de actividades para emisor: {}", emisor.getIdentificacion());

            List<EmisorActividades> actividadesSincronizadas =
                emisorActividadesService.sincronizarActividadesDesdeGometa(emisor);

            response.put("success", true);
            response.put("message", "Sincronización completada exitosamente");
            response.put("data", actividadesSincronizadas);
            response.put("count", actividadesSincronizadas.size());

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Validación fallida: {}", e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {
            log.error("Error sincronizando actividades: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Error en sincronización: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/catalogo/sincronizar")
    public ResponseEntity<Map<String, Object>> sincronizarCatalogoCompleto() {

        Map<String, Object> response = new HashMap<>();

        try {
            log.info("Sincronizando catálogo completo de actividades desde Gometa");

            List<GometaActividadDto> actividades =
                emisorActividadesService.sincronizarCatalogoCompleto();

            response.put("success", true);
            response.put("message", "Catálogo sincronizado exitosamente");
            response.put("data", actividades);
            response.put("count", actividades.size());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error sincronizando catálogo: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Error en sincronización: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Map<String, Object>> validarCodigoActividad(
        @PathVariable String codigo) {

        Map<String, Object> response = new HashMap<>();

        try {
            boolean valido = emisorActividadesService.validarCodigoActividad(codigo);

            response.put("success", true);
            response.put("codigo", codigo);
            response.put("valido", valido);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error validando código: {}", e.getMessage());
            response.put("success", false);
            response.put("error", "Error validando código: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping("/{emisorId}")
    public ResponseEntity<Map<String, Object>> eliminarActividadesDelEmisor(
        @PathVariable Long emisorId) {

        Map<String, Object> response = new HashMap<>();

        try {
            long cantidadAntes = emisorActividadesService.contarActividadesDelEmisor(emisorId);

            emisorActividadesService.eliminarActividadesDelEmisor(emisorId);

            long cantidadDespues = emisorActividadesService.contarActividadesDelEmisor(emisorId);

            response.put("success", true);
            response.put("message", "Actividades eliminadas exitosamente");
            response.put("eliminadas", cantidadAntes - cantidadDespues);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error eliminando actividades: {}", e.getMessage());
            response.put("success", false);
            response.put("error", "Error eliminando actividades: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{emisorId}/count")
    public ResponseEntity<Map<String, Object>> contarActividades(
        @PathVariable Long emisorId) {

        Map<String, Object> response = new HashMap<>();

        try {
            long cantidad = emisorActividadesService.contarActividadesDelEmisor(emisorId);

            response.put("success", true);
            response.put("emisorId", emisorId);
            response.put("total", cantidad);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error contando actividades: {}", e.getMessage());
            response.put("success", false);
            response.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
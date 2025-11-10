package snn.soluciones.com.service.interfaces;

import snn.soluciones.com.dto.GometaActividadDto;
import snn.soluciones.com.models.entity.EmisorActividades;
import snn.soluciones.com.models.entity.Emisor;

import java.util.List;

public interface IEmisorActividadesService {

  /**
   * Obtiene todas las actividades de un emisor
   */
  List<EmisorActividades> findAllByEmisorId(Long emisorId);

  /**
   * Elimina una actividad específica de un emisor
   */
  void deleteByIdAndEmisorId(Long id, Long emisorId);

  /**
   * Guarda una actividad de emisor
   */
  void save(EmisorActividades entity);

  /**
   * Sincroniza las actividades desde la API Gometa (DGT 4.4)
   *
   * @param emisor El emisor para sincronizar
   * @return Lista de actividades sincronizadas y guardadas
   * @throws Exception En caso de error
   */
  List<EmisorActividades> sincronizarActividadesDesdeGometa(Emisor emisor) throws Exception;

  /**
   * Sincroniza el catálogo completo de actividades económicas desde Gometa
   *
   * @return Lista de todas las actividades disponibles
   * @throws Exception En caso de error
   */
  List<GometaActividadDto> sincronizarCatalogoCompleto() throws Exception;

  /**
   * Valida un código de actividad en Gometa
   *
   * @param codigoActividad El código a validar
   * @return true si es válido
   * @throws Exception En caso de error
   */
  boolean validarCodigoActividad(String codigoActividad) throws Exception;

  /**
   * Elimina todas las actividades de un emisor
   */
  void eliminarActividadesDelEmisor(Long emisorId);

  /**
   * Cuenta las actividades de un emisor
   */
  long contarActividadesDelEmisor(Long emisorId);

  /**
   * Método deprecado - Mantener para compatibilidad hacia atrás
   * Usa sincronizarActividadesDesdeGometa internamente
   */
  @Deprecated
  List<EmisorActividades> sincronizarActividadesDesdeHacienda(Emisor emisor) throws Exception;
}
package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CTipoDeIdentificacion;

public interface ICTipoDeIdentificacionService {
  List<CTipoDeIdentificacion> findAll();
  
  CTipoDeIdentificacion findById(Long paramLong);
}

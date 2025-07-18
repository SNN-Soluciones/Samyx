package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.UnidadDeMedida;

public interface IUnidadDeMedidaService {
  List<UnidadDeMedida> findAll();
  
  UnidadDeMedida findById(Long paramLong);
  
  void save(UnidadDeMedida paramUnidadDeMedida);
}

package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.EmisorActividades;

public interface IEmisorActividadesService {
  List<EmisorActividades> findAllByEmisorId(Long paramLong);
  
  void deleteByIdAndEmisorId(Long paramLong1, Long paramLong2);
  
  void save(EmisorActividades paramEmisorActividades);
}

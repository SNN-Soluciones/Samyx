package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CSucursal;

public interface ICSucursalService {
  List<CSucursal> findAllByEmisorId(Long paramLong);
  
  CSucursal findById(Long paramLong);
  
  void save(CSucursal paramCSucursal);
}

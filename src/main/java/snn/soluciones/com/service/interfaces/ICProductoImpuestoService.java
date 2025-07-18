package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CProductoImpuesto;

public interface ICProductoImpuestoService {
  void save(CProductoImpuesto paramCProductoImpuesto);
  
  void deleteById(Long paramLong);
  
  List<CProductoImpuesto> findAllByIdProducto(Long paramLong);
}

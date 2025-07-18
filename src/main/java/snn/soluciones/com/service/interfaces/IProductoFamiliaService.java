package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.ProductoFamilia;

public interface IProductoFamiliaService {
  List<ProductoFamilia> findAllByEmisorId(Long paramLong);
  
  ProductoFamilia save(ProductoFamilia paramProductoFamilia);
  
  ProductoFamilia findByIdAndEmisorId(Long paramLong1, Long paramLong2);
}

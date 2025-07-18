package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.ProductoBodega;

public interface IProductoBodegaService {
  List<ProductoBodega> findAllByEmisorId(Long paramLong);
  
  ProductoBodega save(ProductoBodega paramProductoBodega);
  
  ProductoBodega findByIdAndEmisorId(Long paramLong1, Long paramLong2);
}

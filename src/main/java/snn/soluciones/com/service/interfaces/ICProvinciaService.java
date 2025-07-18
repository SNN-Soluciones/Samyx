package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CProvincia;

public interface ICProvinciaService {
  List<CProvincia> findAll();
  
  CProvincia findById(Long paramLong);
}

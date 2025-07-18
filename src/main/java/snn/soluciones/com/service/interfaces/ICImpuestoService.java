package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CImpuesto;

public interface ICImpuestoService {
  List<CImpuesto> findAllImpuestos();
  
  CImpuesto findById(Long paramLong);
  
  void save(CImpuesto paramCImpuesto);
  
  void deleteById(Long paramLong);
}

package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CDistrito;

public interface ICDistritoService {
  List<CDistrito> findAllByIdCanton(Long paramLong);
  
  CDistrito findById(Long paramLong);
}

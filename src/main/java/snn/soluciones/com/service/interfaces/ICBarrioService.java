package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CBarrio;

public interface ICBarrioService {
  List<CBarrio> findAllByIdDistrito(Long paramLong);
  
  CBarrio findById(Long paramLong);
}

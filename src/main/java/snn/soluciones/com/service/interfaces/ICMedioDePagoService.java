package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CMedioDePago;

public interface ICMedioDePagoService {
  List<CMedioDePago> findAll();
  
  List<CMedioDePago> findAllIn(List<Long> paramList);
  
  CMedioDePago findById(Long paramLong);
}

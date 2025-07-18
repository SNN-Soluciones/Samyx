package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.Moneda;

public interface IMonedaService {
  List<Moneda> findAll();
  
  Moneda findById(Long paramLong);
}

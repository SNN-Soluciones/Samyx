package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CCodigosTarifasIva;

public interface ICCodigosTarifasIvaService {
  List<CCodigosTarifasIva> findAll();
  
  CCodigosTarifasIva findById(Long paramLong);
}

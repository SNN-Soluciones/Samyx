package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.CCodigoReferencia;

public interface ICCodigoReferenciaService {
  List<CCodigoReferencia> findAll();
  
  CCodigoReferencia findById(Long paramLong);
}

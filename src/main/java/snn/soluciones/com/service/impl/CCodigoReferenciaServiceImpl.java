package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICCodigoReferenciaDao;
import snn.soluciones.com.models.entity.CCodigoReferencia;
import snn.soluciones.com.service.interfaces.ICCodigoReferenciaService;

@Service
public class CCodigoReferenciaServiceImpl implements ICCodigoReferenciaService {
  @Autowired
  private ICCodigoReferenciaDao _codigoReferenciaDao;
  
  public List<CCodigoReferencia> findAll() {
    return (List<CCodigoReferencia>)this._codigoReferenciaDao.findAll();
  }
  
  public CCodigoReferencia findById(Long id) {
    return this._codigoReferenciaDao.findById(id).orElse(null);
  }
}

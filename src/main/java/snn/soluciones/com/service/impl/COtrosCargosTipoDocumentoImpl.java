package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICOtrosCargosTipoDocumentoDao;
import snn.soluciones.com.models.entity.COtrosCargosTipoDocumento;

@Service
public class COtrosCargosTipoDocumentoImpl {
  @Autowired
  private ICOtrosCargosTipoDocumentoDao _dao;
  
  public List<COtrosCargosTipoDocumento> findAll() {
    return (List<COtrosCargosTipoDocumento>)this._dao.findAll();
  }
}

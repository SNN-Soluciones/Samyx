package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.ICTipoDocumentoExoneracionOAutorizacionDao;
import snn.soluciones.com.models.entity.CTipoDocumentoExoneracionOAutorizacion;
import snn.soluciones.com.service.interfaces.ICTipoDocumentoExoneracionOAutorizacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CTipoDocumentoExoneracionOAutorizacionServiceImpl implements ICTipoDocumentoExoneracionOAutorizacionService {
  @Autowired
  private ICTipoDocumentoExoneracionOAutorizacionDao _dao;
  
  public List<CTipoDocumentoExoneracionOAutorizacion> findAll() {
    return (List<CTipoDocumentoExoneracionOAutorizacion>)this._dao.findAll();
  }
}

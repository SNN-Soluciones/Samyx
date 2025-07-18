package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.ICTipoDeDocumentoReferenciaDao;
import snn.soluciones.com.models.entity.CTipoDeDocumentoReferencia;
import snn.soluciones.com.service.interfaces.ICTipoDeDocumentoReferenciaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CTipoDeDocumentoReferenciaServiceImpl implements ICTipoDeDocumentoReferenciaService {
  @Autowired
  private ICTipoDeDocumentoReferenciaDao _dao;
  
  public List<CTipoDeDocumentoReferencia> findAll() {
    return (List<CTipoDeDocumentoReferencia>)this._dao.findAll();
  }
}

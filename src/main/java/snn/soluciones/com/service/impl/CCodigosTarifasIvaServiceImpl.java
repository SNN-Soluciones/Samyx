package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICCodigosTarifasIvaDao;
import snn.soluciones.com.models.entity.CCodigosTarifasIva;
import snn.soluciones.com.service.interfaces.ICCodigosTarifasIvaService;

@Service
public class CCodigosTarifasIvaServiceImpl implements ICCodigosTarifasIvaService {
  @Autowired
  private ICCodigosTarifasIvaDao _dao;
  
  public List<CCodigosTarifasIva> findAll() {
    return (List<CCodigosTarifasIva>)this._dao.findAll();
  }
  
  public CCodigosTarifasIva findById(Long id) {
    return this._dao.findById(id).orElse(null);
  }
}

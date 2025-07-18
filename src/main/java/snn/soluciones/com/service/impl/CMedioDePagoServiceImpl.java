package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snn.soluciones.com.models.dao.ICMedioDePagoDao;
import snn.soluciones.com.models.entity.CMedioDePago;
import snn.soluciones.com.service.interfaces.ICMedioDePagoService;

@Service
public class CMedioDePagoServiceImpl implements ICMedioDePagoService {
  @Autowired
  private ICMedioDePagoDao _medioDePagoDao;
  
  @Transactional(readOnly = true)
  public List<CMedioDePago> findAll() {
    return (List<CMedioDePago>)this._medioDePagoDao.findAll();
  }
  
  public List<CMedioDePago> findAllIn(List<Long> md) {
    return this._medioDePagoDao.findAllIn(md);
  }
  
  public CMedioDePago findById(Long id) {
    return this._medioDePagoDao.findById(id).orElse(null);
  }
}

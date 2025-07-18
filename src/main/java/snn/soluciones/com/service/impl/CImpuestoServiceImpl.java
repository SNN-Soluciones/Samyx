package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICImpuestoDao;
import snn.soluciones.com.models.entity.CImpuesto;
import snn.soluciones.com.service.interfaces.ICImpuestoService;

@Service
public class CImpuestoServiceImpl implements ICImpuestoService {
  @Autowired
  private ICImpuestoDao _impuestoDao;
  
  public List<CImpuesto> findAllImpuestos() {
    return this._impuestoDao.findAllImpuestos();
  }
  
  public CImpuesto findById(Long id) {
    return this._impuestoDao.findById(id).orElse(null);
  }
  
  public void save(CImpuesto entity) {
    this._impuestoDao.save(entity);
  }
  
  public void deleteById(Long id) {
    this._impuestoDao.deleteById(id);
  }
}

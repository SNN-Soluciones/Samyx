package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICProductoImpuestoDao;
import snn.soluciones.com.models.entity.CProductoImpuesto;
import snn.soluciones.com.service.interfaces.ICProductoImpuestoService;

@Service
public class CProductoImpuestoServiceImpl implements ICProductoImpuestoService {
  @Autowired
  private ICProductoImpuestoDao _productoImpuestoDao;
  
  public void save(CProductoImpuesto entity) {
    this._productoImpuestoDao.save(entity);
  }
  
  public List<CProductoImpuesto> findAllByIdProducto(Long id) {
    return this._productoImpuestoDao.findAllByIdProducto(id);
  }
  
  public void deleteById(Long id) {
    this._productoImpuestoDao.deleteById(id);
  }
}

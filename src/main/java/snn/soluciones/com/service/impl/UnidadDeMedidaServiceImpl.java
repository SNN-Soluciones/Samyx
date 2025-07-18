package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.IUnidadDeMedidaDao;
import snn.soluciones.com.models.entity.UnidadDeMedida;
import snn.soluciones.com.service.interfaces.IUnidadDeMedidaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnidadDeMedidaServiceImpl implements IUnidadDeMedidaService {
  @Autowired
  private IUnidadDeMedidaDao _unidadDeMedidaDao;
  
  public List<UnidadDeMedida> findAll() {
    return (List<UnidadDeMedida>)this._unidadDeMedidaDao.findAll();
  }
  
  public UnidadDeMedida findById(Long id) {
    return this._unidadDeMedidaDao.findById(id).orElse(null);
  }
  
  public void save(UnidadDeMedida entity) {
    this._unidadDeMedidaDao.save(entity);
  }
}

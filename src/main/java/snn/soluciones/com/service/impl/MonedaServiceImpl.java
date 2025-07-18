package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.IMonedaDao;
import snn.soluciones.com.models.entity.Moneda;
import snn.soluciones.com.service.interfaces.IMonedaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonedaServiceImpl implements IMonedaService {
  @Autowired
  private IMonedaDao _monedaDao;
  
  public List<Moneda> findAll() {
    return (List<Moneda>)this._monedaDao.findAll();
  }
  
  public Moneda findById(Long id) {
    return this._monedaDao.findById(id).orElse(null);
  }
}

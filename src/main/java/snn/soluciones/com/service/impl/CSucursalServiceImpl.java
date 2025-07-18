package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.ICSucursalDao;
import snn.soluciones.com.models.entity.CSucursal;
import snn.soluciones.com.service.interfaces.ICSucursalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSucursalServiceImpl implements ICSucursalService {
  @Autowired
  private ICSucursalDao _sucursalDao;
  
  public List<CSucursal> findAllByEmisorId(Long id) {
    return this._sucursalDao.findAllByEmisorId(id);
  }
  
  public void save(CSucursal entity) {
    this._sucursalDao.save(entity);
  }
  
  public CSucursal findById(Long id) {
    return this._sucursalDao.findById(id).orElse(null);
  }
}

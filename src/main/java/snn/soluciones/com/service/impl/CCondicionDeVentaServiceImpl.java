package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICCondicionDeVentaDao;
import snn.soluciones.com.models.entity.CCondicionDeVenta;
import snn.soluciones.com.service.interfaces.ICCondicionDeVentaService;

@Service
public class CCondicionDeVentaServiceImpl implements ICCondicionDeVentaService {
  @Autowired
  private ICCondicionDeVentaDao _condicionDeVentaDao;
  
  public List<CCondicionDeVenta> findAll() {
    return (List<CCondicionDeVenta>)this._condicionDeVentaDao.findAll();
  }
}

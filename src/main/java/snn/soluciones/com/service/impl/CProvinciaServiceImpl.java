package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.ICProvinciaDao;
import snn.soluciones.com.models.entity.CProvincia;
import snn.soluciones.com.service.interfaces.ICProvinciaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CProvinciaServiceImpl implements ICProvinciaService {
  @Autowired
  private ICProvinciaDao _provinciaDao;
  
  public List<CProvincia> findAll() {
    return (List<CProvincia>)this._provinciaDao.findAll();
  }
  
  public CProvincia findById(Long id) {
    return this._provinciaDao.findById(id).orElse(null);
  }
}

package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICDistritoDao;
import snn.soluciones.com.models.entity.CDistrito;
import snn.soluciones.com.service.interfaces.ICDistritoService;

@Service
public class CDistritoServiceImpl implements ICDistritoService {
  @Autowired
  private ICDistritoDao _distritoDao;
  
  public List<CDistrito> findAllByIdCanton(Long id) {
    return this._distritoDao.findAllByIdCanton(id);
  }
  
  public CDistrito findById(Long id) {
    return this._distritoDao.findById(id).orElse(null);
  }
}

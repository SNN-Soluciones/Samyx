package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICCantonDao;
import snn.soluciones.com.models.entity.CCanton;
import snn.soluciones.com.service.interfaces.ICCantonService;

@Service
public class CCantonServiceImpl implements ICCantonService {
  @Autowired
  private ICCantonDao _cantonDao;
  
  public List<CCanton> findAllByIdProvincia(Long id) {
    return this._cantonDao.findAllByIdProvincia(id);
  }
  
  public CCanton findById(Long id) {
    return this._cantonDao.findById(id).orElse(null);
  }
}

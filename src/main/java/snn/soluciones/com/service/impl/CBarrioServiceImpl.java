package snn.soluciones.com.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snn.soluciones.com.models.dao.ICBarrioDao;
import snn.soluciones.com.models.entity.CBarrio;
import snn.soluciones.com.service.interfaces.ICBarrioService;

@Service
public class CBarrioServiceImpl implements ICBarrioService {
  @Autowired
  private ICBarrioDao _barrioDao;
  
  public List<CBarrio> findAllByIdDistrito(Long id) {
    return this._barrioDao.findAllByIdDistrito(id);
  }
  
  public CBarrio findById(Long id) {
    return this._barrioDao.findById(id).orElse(null);
  }
}

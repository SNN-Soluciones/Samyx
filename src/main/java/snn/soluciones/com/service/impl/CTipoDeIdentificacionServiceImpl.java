package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.ICTipoDeIdentificacionDao;
import snn.soluciones.com.models.entity.CTipoDeIdentificacion;
import snn.soluciones.com.service.interfaces.ICTipoDeIdentificacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CTipoDeIdentificacionServiceImpl implements ICTipoDeIdentificacionService {
  @Autowired
  private ICTipoDeIdentificacionDao _tipoDeIdentificacionDao;
  
  public List<CTipoDeIdentificacion> findAll() {
    return (List<CTipoDeIdentificacion>)this._tipoDeIdentificacionDao.findAll();
  }
  
  public CTipoDeIdentificacion findById(Long id) {
    return this._tipoDeIdentificacionDao.findById(id).orElse(null);
  }
}

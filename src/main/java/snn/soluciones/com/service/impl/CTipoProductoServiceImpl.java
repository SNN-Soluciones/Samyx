package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.ICTipoProductoDao;
import snn.soluciones.com.models.entity.CTipoProducto;
import snn.soluciones.com.service.interfaces.ICTipoProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CTipoProductoServiceImpl implements ICTipoProductoService {
  @Autowired
  private ICTipoProductoDao _tipoProductoDao;
  
  public List<CTipoProducto> findAll() {
    return (List<CTipoProducto>)this._tipoProductoDao.findAll();
  }
}

package snn.soluciones.com.service.impl;

import snn.soluciones.com.models.dao.IRoleUsuarioDao;
import snn.soluciones.com.models.entity.RolesUsuario;
import snn.soluciones.com.service.interfaces.IRolesUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesUsuarioServiceImpl implements IRolesUsuarioService {
  @Autowired
  private IRoleUsuarioDao _rolesUsuarioDao;
  
  public List<RolesUsuario> findAll() {
    return (List<RolesUsuario>)this._rolesUsuarioDao.findAll();
  }
}

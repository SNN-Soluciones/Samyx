package snn.soluciones.com.models.dao;

import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.RolesUsuario;

public interface IRoleUsuarioDao extends CrudRepository<RolesUsuario, Long> {}

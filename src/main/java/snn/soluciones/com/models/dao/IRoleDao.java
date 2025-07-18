package snn.soluciones.com.models.dao;

import org.springframework.data.repository.CrudRepository;
import snn.soluciones.com.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {}

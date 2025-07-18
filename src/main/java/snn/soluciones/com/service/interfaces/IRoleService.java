package snn.soluciones.com.service.interfaces;


import snn.soluciones.com.models.entity.Role;

public interface IRoleService {
  void save(Role paramRole);
  
  Role findById(Long paramLong);
  
  void deleteById(Long paramLong);
  
  void addRole(String paramString, Long paramLong);
}

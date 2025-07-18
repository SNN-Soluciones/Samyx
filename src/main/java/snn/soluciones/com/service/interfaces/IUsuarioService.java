package snn.soluciones.com.service.interfaces;

import java.util.List;
import snn.soluciones.com.models.entity.Usuario;

public interface IUsuarioService {
  void save(Usuario paramUsuario);
  
  Usuario findByUsername(String paramString);
  
  Usuario findById(Long paramLong);
  
  void updatePassword(String paramString, Long paramLong);
  
  List<Usuario> findAll();
  
  void deleteById(Long paramLong);
  
  Usuario findByUsuarioOrEmail(String paramString);
  
  Usuario verificarSiExisteByUsuarioOrEmail(String paramString1, String paramString2);
}

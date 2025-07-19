package snn.soluciones.com.models.entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "authority"})})
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String authority;
  
  private static final long serialVersionUID = 1L;

  public String getDetalleRole() {
    String response = "";
    switch (getAuthority()) {
      case "ROLE_SUPER_ADMIN":
        response = "Super admin";
        break;
      case "ROLE_ADMIN":
        response = "Full permisos";
        break;
      case "ROLE_USER":
        response = "Administrador";
        break;
      case "ROLE_USER_COBRADOR":
        response = "Solo factura";
        break;
      case "ROLE_CAJAS_FULL":
        response = "Control cajas detallado";
        break;
    } 
    return response;
  }
}

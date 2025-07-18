package snn.soluciones.com.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "roles_usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"rol_usuario"})})
public class RolesUsuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "rol_usuario")
  private String rolUsuario;
  
  @Column(name = "details_authority")
  private String detailsAuthority;

}

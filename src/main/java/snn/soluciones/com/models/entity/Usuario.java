package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class Usuario implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String nombre;
  
  @Column(name = "apellido_p")
  private String apellidoP;
  
  @Column(name = "apellido_m")
  private String apellidoM;
  
  private String telefono;
  
  @Column(length = 100, unique = true)
  private String email;
  
  private String direccion;
  
  @Column(length = 30, unique = true)
  private String username;
  
  @Column(length = 60)
  private String password;
  
  private Boolean enabled;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private List<Role> roles = new ArrayList<>();
  
  private static final long serialVersionUID = 1L;

  public void addRoleUsuario(Role rol) {
    this.roles.add(rol);
  }
}

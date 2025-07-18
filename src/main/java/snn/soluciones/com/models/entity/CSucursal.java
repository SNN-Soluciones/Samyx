package snn.soluciones.com.models.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "c_sucursales", uniqueConstraints = {@UniqueConstraint(columnNames = {"emisor_id", "sucursal"})})
public class CSucursal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(length = 3)
  private int sucursal;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Emisor emisor;
  
  @Column(name = "nombre_sucursal", length = 200)
  private String nombreSucursal;
  
  @Column(length = 8)
  private String telefono;
  
  @Column(name = "correo_sucursal", length = 100)
  private String correoSucursal;
  
  private String direccion;
  
  @Column(name = "encargado_sucursal", length = 150)
  private String encargadoSucursal;
  
  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "user_id")
  private Usuario usuario;
  
  @Column(length = 1)
  private int status;

}

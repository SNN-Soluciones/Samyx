package snn.soluciones.com.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

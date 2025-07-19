package snn.soluciones.com.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "c_cabys")
public class CCabys {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "codigo_hacienda", length = 13)
  private String codigoHacienda;
  
  @Column(name = "producto_servicio")
  private String productoServicio;
  
  @Column(length = 100)
  private String impuesto;

}

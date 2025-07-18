package snn.soluciones.com.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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

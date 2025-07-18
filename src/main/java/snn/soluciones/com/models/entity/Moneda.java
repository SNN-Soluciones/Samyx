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
@Table(name = "c_monedas")
public class Moneda {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(length = 1)
  private String abreviatura;
  
  @Column(name = "simbolo_moneda", length = 5, unique = true)
  private String simboloMoneda;
  
  @Column(name = "descripcion_moneda", length = 50)
  private String descripcionMoneda;

}
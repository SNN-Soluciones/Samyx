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
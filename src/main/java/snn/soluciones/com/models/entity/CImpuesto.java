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
@Table(name = "c_impuestos")
public class CImpuesto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "detalle_impuesto", unique = true)
  private String detalleImpuesto;
  
  public String getLabelImpuesto() {
    String res = "" + getId();
    if (getId() < 9L) {
      res = "0" + res;
    }
    return res;
  }

}

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
@Table(name = "c_codigos_tarifas_iva")
public class CCodigosTarifasIva {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(length = 4)
  private String tarifa;
  
  @Column(name = "detalle_tarifa_impuesto_iva", length = 100)
  private String detalleTarifaImpuestoIva;
  
  public String getLabelTarifa() {
    String res = "" + getId();
    if (getId().longValue() < 9L) {
      res = "0" + res;
    }
    return res;
  }

}
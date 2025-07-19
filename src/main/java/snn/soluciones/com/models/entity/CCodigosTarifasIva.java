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
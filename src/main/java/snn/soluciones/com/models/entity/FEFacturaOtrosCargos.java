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
@Table(name = "fe_facturas_otros_cargos")
public class FEFacturaOtrosCargos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "tipoDocumento", length = 2)
  private String tipoDocumento;
  
  @Column(name = "numero_identidad_tercero", length = 12)
  private String numeroIdentidadTercero;
  
  @Column(name = "nombre_tercero", length = 100)
  private String nombreTercero;
  
  @Column(length = 160)
  private String detalle;
  
  @Column(length = 11)
  private String porcentaje;
  
  @Column(name = "monto_cargo", length = 20)
  private String montoCargo;

}

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
@Table(name = "fe_facturas_items_impuestos_exoneraciones")
public class FEExoneracionImpuestoItemFactura {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "tipo_documento", length = 2)
  private String tipoDocumento;
  
  @Column(name = "numero_documento", length = 17)
  private String numeroDocumento;
  
  @Column(name = "nombre_institucion", length = 100)
  private String nombreInstitucion;
  
  @Column(name = "fecha_emision", length = 30)
  private String fechaEmision;
  
  @Column(name = "monto_impuesto", precision = 18, scale = 5)
  private Double montoImpuesto;
  
  @Column(name = "porcentaje_Compra", length = 3)
  private int porcentajeCompra;

}
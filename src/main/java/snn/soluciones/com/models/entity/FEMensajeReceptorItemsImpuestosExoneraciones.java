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
@Table(name = "fe_mensaje_receptor_items_impuestos_exoneraciones")
public class FEMensajeReceptorItemsImpuestosExoneraciones {
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
  
  @Column(name = "monto_impuesto", precision = 18)
  private Double montoImpuesto;
  
  @Column(name = "porcentaje_Compra", length = 3)
  private int porcentajeCompra;

}
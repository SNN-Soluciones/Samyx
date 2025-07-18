package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "c_producto_impuestos", uniqueConstraints = {@UniqueConstraint(columnNames = {"producto_id", "impuesto_id"})})
public class CProductoImpuesto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "impuesto_id")
  private CImpuesto impuesto;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "codigos_tarifas_iva_id")
  private CCodigosTarifasIva codigosTarifasIva;
  
  private Double porcentaje;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  private Producto producto;

}

package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

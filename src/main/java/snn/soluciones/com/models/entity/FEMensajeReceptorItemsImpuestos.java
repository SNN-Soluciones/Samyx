package snn.soluciones.com.models.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "fe_mensaje_receptor_items_impuestos")
public class FEMensajeReceptorItemsImpuestos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "impuestos_item_factura_id")
  private List<FEMensajeReceptorItemsImpuestosExoneraciones> exoneracionImpuestoItemFactura = new ArrayList<>();
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "impuesto_id")
  private CImpuesto impuesto;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "codigo_tarifa_iva_id")
  private CCodigosTarifasIva codigoTarifaIva;
  
  @Column(precision = 20)
  private Double tarifa;
  
  @Column(precision = 20)
  private Double monto;

  public void addItemFacturaImpuestosExoneracion(FEMensajeReceptorItemsImpuestosExoneraciones item) {
    this.exoneracionImpuestoItemFactura.add(item);
  }

}
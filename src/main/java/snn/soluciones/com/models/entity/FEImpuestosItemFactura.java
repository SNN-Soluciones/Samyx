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

@Entity
@Table(name = "fe_facturas_items_impuestos")
public class FEImpuestosItemFactura {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "impuestos_item_factura_id")
  private List<FEExoneracionImpuestoItemFactura> exoneracionImpuestoItemFactura = new ArrayList<>();
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "impuesto_id")
  private CImpuesto impuesto;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "codigo_tarifa_iva_id")
  private CCodigosTarifasIva codigoTarifaIva;
  
  @Column(precision = 20)
  private Double tarifa;
  
  @Column(name = "factor_iva", precision = 4)
  private Double factorIva;
  
  @Column(precision = 20)
  private Double monto;
  
  @Column(name = "monto_exportacion", precision = 20)
  private Double montoExportacion;
  
  public Long getId() {
    return this.id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public List<FEExoneracionImpuestoItemFactura> getExoneracionImpuestoItemFactura() {
    return this.exoneracionImpuestoItemFactura;
  }
  
  public void setExoneracionImpuestoItemFactura(List<FEExoneracionImpuestoItemFactura> exoneracionImpuestoItemFactura) {
    this.exoneracionImpuestoItemFactura = exoneracionImpuestoItemFactura;
  }
  
  public CImpuesto getImpuesto() {
    return this.impuesto;
  }
  
  public void setImpuesto(CImpuesto impuesto) {
    this.impuesto = impuesto;
  }
  
  public CCodigosTarifasIva getCodigoTarifaIva() {
    return this.codigoTarifaIva;
  }
  
  public void setCodigoTarifaIva(CCodigosTarifasIva codigoTarifaIva) {
    this.codigoTarifaIva = codigoTarifaIva;
  }
  
  public Double getTarifa() {
    return this.tarifa;
  }
  
  public void setTarifa(Double tarifa) {
    this.tarifa = tarifa;
  }
  
  public Double getMonto() {
    return this.monto;
  }
  
  public void setMonto(Double monto) {
    this.monto = monto;
  }
  
  public Double getMontoExportacion() {
    return this.montoExportacion;
  }
  
  public void setMontoExportacion(Double montoExportacion) {
    this.montoExportacion = montoExportacion;
  }
  
  public void addItemFacturaImpuestosExoneracion(FEExoneracionImpuestoItemFactura item) {
    this.exoneracionImpuestoItemFactura.add(item);
  }
}
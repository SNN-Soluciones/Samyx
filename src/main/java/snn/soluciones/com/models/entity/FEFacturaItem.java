package snn.soluciones.com.models.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "fe_facturas_items")
public class FEFacturaItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "item_factura_id")
  private List<FEImpuestosItemFactura> impuestosItemFactura;
  
  @Column(name = "numero_linea")
  private int numeroLinea;
  
  @Column(name = "partida_arancelaria", length = 12)
  private String partidaArancelaria;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "producto_id")
  private Producto producto;
  
  @Column(name = "detalle_producto", length = 160)
  private String detalleProducto;
  
  @Column(precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double cantidad;
  
  @Column(precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double unidades;
  
  @Column(name = "precio_unitario", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double precioUnitario;
  
  @Column(name = "monto_total", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double montoTotal;
  
  @Column(name = "sub_total", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double subTotal;
  
  @Column(name = "monto_descuento", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double montoDescuento;
  
  @Column(name = "naturaleza_descuento")
  private String naturalezaDescuento;
  
  @Column(name = "impuesto_neto", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double impuestoNeto;
  
  @Column(name = "monto_total_linea", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double montoTotalLinea;
  
  @Column(name = "tipo_venta_linea", length = 1)
  private String tipoVentaLinea;
  
  @Column(name = "fracciones_porUnidad", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double fraccionesPorUnidad;
  
  @Column(precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double fraccion;
  
  @Column(name = "precio_fraccion", precision = 18, scale = 5, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double precioFraccion;
  
  public Double getTotalImpuestos() {
    Double response = Double.valueOf(0.0D);
    for (FEImpuestosItemFactura i : this.impuestosItemFactura) {
      if (i.getImpuesto().getId().longValue() == 1L) {
        response = Double.valueOf(response.doubleValue() + i.getMonto().doubleValue());
      }
    } 
    return response;
  }
  
  public Double getPorcentajeIva() {
    Double response = Double.valueOf(0.0D);
    for (FEImpuestosItemFactura i : this.impuestosItemFactura) {
      if (i.getImpuesto().getId().longValue() == 1L) {
        response = Double.valueOf(response.doubleValue() + i.getTarifa().doubleValue());
      }
    } 
    return response;
  }
  
  public FEFacturaItem() {
    this.impuestosItemFactura = new ArrayList<>();
  }
  
  public Double getPorcentajeDescuento() {
    Double resp = Double.valueOf(0.0D);
    resp = Double.valueOf(this.montoDescuento.doubleValue() / this.montoTotal.doubleValue() * 100.0D);
    return redondearDecimales(resp.doubleValue(), 2);
  }
  
  public Double redondearDecimales(double valorInicial, int numeroDecimales) {
    double resultado = valorInicial;
    double parteEntera = Math.floor(resultado);
    resultado = (resultado - parteEntera) * Math.pow(10.0D, numeroDecimales);
    resultado = Math.round(resultado);
    resultado = resultado / Math.pow(10.0D, numeroDecimales) + parteEntera;
    return Double.valueOf(resultado);
  }

  public void addFEImpuestosItemFactura(FEImpuestosItemFactura item) {
    this.impuestosItemFactura.add(item);
  }

}
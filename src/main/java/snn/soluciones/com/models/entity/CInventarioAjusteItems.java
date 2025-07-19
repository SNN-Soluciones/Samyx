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
@Table(name = "c_inventario_movimientos_items_ajustes")
public class CInventarioAjusteItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "item_factura_id")
  private List<CInventarioAjusteItemsImpuesto> impuestosItem;
  
  @Column(name = "numero_linea")
  private int numeroLinea;
  
  @Column(name = "partida_arancelaria", length = 12)
  private String partidaArancelaria;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "producto_id")
  private Producto producto;
  
  @Column(name = "detalle_producto", length = 160)
  private String detalleProducto;
  
  @Column(precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double cantidad;
  
  @Column(precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double unidades;
  
  @Column(name = "precio_unitario", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double precioUnitario;
  
  @Column(name = "monto_total", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double montoTotal;
  
  @Column(name = "sub_total", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double subTotal;
  
  @Column(name = "monto_descuento", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double montoDescuento;
  
  @Column(name = "naturaleza_descuento")
  private String naturalezaDescuento;
  
  @Column(name = "impuesto_neto", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double impuestoNeto;
  
  @Column(name = "monto_total_linea", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double montoTotalLinea;
  
  @Column(name = "tipo_venta_linea", length = 1)
  private String tipoVentaLinea;
  
  @Column(name = "fracciones_porUnidad", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double fraccionesPorUnidad;
  
  @Column(precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double fraccion;
  
  @Column(name = "precio_fraccion", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double precioFraccion;
  
  public Double getTotalImpuestos() {
    Double response = Double.valueOf(0.0D);
    for (CInventarioAjusteItemsImpuesto i : this.impuestosItem) {
      if (i.getImpuesto().getId().longValue() == 1L) {
        response = Double.valueOf(response.doubleValue() + i.getMonto().doubleValue());
      }
    } 
    return response;
  }
  
  public CInventarioAjusteItems() {
    this.impuestosItem = new ArrayList<>();
  }

  public void addCInventarioAjusteItemsImpuesto(CInventarioAjusteItemsImpuesto item) {
    this.impuestosItem.add(item);
  }

}

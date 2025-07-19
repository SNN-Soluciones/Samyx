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
@Table(name = "c_inventario_movimientos_items")
public class CInventarioMovimientoItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "item_factura_id")
  private List<CInventarioMovimientoItemsImpuesto> impuestosItem;
  
  @Column(name = "numero_linea")
  private int numeroLinea;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "producto_id")
  private Producto producto;
  
  @Column(name = "detalle_producto", length = 255)
  private String detalleProducto;
  
  @Column(precision = 18)
  private Double cantidad;
  
  @Column(name = "precio_compra", precision = 18)
  private Double precioCompra;
  
  @Column(name = "precio_promediado", precision = 18)
  private Double precioPromediado;
  
  @Column(name = "precio_unitario", precision = 18)
  private Double precioUnitario;
  
  @Column(name = "monto_total", precision = 18)
  private Double montoTotal;
  
  @Column(name = "sub_total", precision = 18)
  private Double subTotal;
  
  @Column(name = "monto_descuento", precision = 18)
  private Double montoDescuento;
  
  @Column(name = "naturaleza_descuento")
  private String naturalezaDescuento;
  
  @Column(name = "monto_total_linea", precision = 18)
  private Double montoTotalLinea;
  
  public Double getTotalImpuestos() {
    Double response = Double.valueOf(0.0D);
    for (CInventarioMovimientoItemsImpuesto i : this.impuestosItem) {
      if (i.getImpuesto().getId().longValue() == 1L) {
        response = Double.valueOf(response.doubleValue() + i.getMonto().doubleValue());
      }
    } 
    return response;
  }
  
  public CInventarioMovimientoItems() {
    this.impuestosItem = new ArrayList<>();
  }

  public void addCInventarioMovimientoItemsImpuesto(CInventarioMovimientoItemsImpuesto item) {
    this.impuestosItem.add(item);
  }
}

package snn.soluciones.com.models.entity;

import java.util.ArrayList;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "fe_mensaje_receptor_items")
public class FEMensajeReceptorItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "item_factura_id")
  private List<FEMensajeReceptorItemsImpuestos> impuestosItemFactura;
  
  @Column(name = "numero_linea")
  private int numeroLinea;
  
  @Column(name = "codigo", length = 20)
  private String codigo;
  
  @Column(name = "detalle_producto", length = 250)
  private String detalleProducto;
  
  @Column(precision = 18)
  private Double cantidad;
  
  @Column(name = "unidad_de_medida", length = 10)
  private String unidadDeMedida;
  
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
  
  @Column(name = "impuesto_neto", precision = 20)
  private Double impuestoNeto;
  
  @Column(name = "monto_total_linea", precision = 18)
  private Double montoTotalLinea;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "producto_id")
  private Producto producto;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_aplico_id")
  private Usuario usuarioAplico;
  
  @Column(precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double utilidad;
  
  @Column(name = "precio_actual_catalogo", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double precioActualCatalogo;
  
  @Column(name = "utilidad_fraccion", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double utilidadFraccion;
  
  @Column(name = "precio_venta_sin_iva", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double precioVentaSinIva;
  
  @Column(name = "precio_venta_fraccion_sin_iva", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double precioVentaFraccionSinIva;
  
  @Column(name = "estado_inventario_linea", length = 1)
  public String estadoInventarioLinea;
  
  @Column(name = "fecha_aplicacion_inventario")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  private Date fechaAplicacionInventario;
  
  public Double getTotalImpuestos() {
    Double response = Double.valueOf(0.0D);
    for (FEMensajeReceptorItemsImpuestos i : this.impuestosItemFactura) {
      if (i.getImpuesto().getId().longValue() == 1L) {
        response = Double.valueOf(response.doubleValue() + i.getMonto().doubleValue());
      }
    } 
    return response;
  }
  
  public FEMensajeReceptorItems() {
    this.impuestosItemFactura = new ArrayList<>();
  }

  public void addFEImpuestosItemFactura(FEMensajeReceptorItemsImpuestos item) {
    this.impuestosItemFactura.add(item);
  }

}
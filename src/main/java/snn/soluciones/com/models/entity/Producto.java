package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "productos", uniqueConstraints = {@UniqueConstraint(columnNames = {"codigo", "emisor_id"})})
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(length = 20)
  private String codigo;
  
  @Column(length = 30)
  private String codigoBarras;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_producto_id")
  private CTipoProducto tipoProducto;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "cabys_id")
  private EmisorCabys emisorCabys;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "familia_producto_id")
  private ProductoFamilia productoFamilia;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "producto_id")
  @JsonManagedReference
  private List<CProductoImpuesto> productoImpuesto;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "proveedor_id")
  @JsonBackReference
  private Cliente proveedor;
  
  @Column(length = 200)
  private String nombreProducto;
  
  @Column(name = "aplica_devolucion_iva", length = 1)
  private String aplicaDevolucionIva;
  
  @Column(name = "tipo_venta", length = 1)
  private String tipoVenta;
  
  @Column(name = "fracciones_por_unidad", precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double fraccionesPorUnidad;
  
  @Column(name = "precio_compra", precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double precioCompra;
  
  @Column(precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double utilidad;
  
  @Column(name = "utilidad_fraccion", precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double utilidadFraccion;
  
  @Column(name = "precio_promediado", precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double precioPromediado;
  
  @Column(length = 20, precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double precio;
  
  @Column(name = "precio_fraccion", precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double precioFraccion;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "moneda_id")
  private Moneda moneda;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unidad_de_medida_id")
  private UnidadDeMedida unidadDeMedida;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  @JsonBackReference
  private Usuario usuario;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emisor_id")
  @JsonBackReference
  private Emisor emisor;
  
  @Column(name = "producto_exento", length = 1)
  private String productoExento;
  
  @Column(name = "afecta_inventario", length = 1)
  private String afectaInventario;
  
  public void addImpuestoProducto(CProductoImpuesto item) {
    this.productoImpuesto.add(item);
  }
  
  @Column(precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double entradas = Double.valueOf(0.0D);
  
  @Column(precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double salidas = Double.valueOf(0.0D);
  
  @Column(name = "existencia_minima", precision = 18, scale = 5, columnDefinition = "Double default 0.00")
  private Double existenciaMinima = Double.valueOf(0.0D);
  
  @Column(name = "estado_producto", length = 1)
  private String estadoProducto;

  public Double getImpuestoTotal() {
    Double res = Double.valueOf(0.0D);
    for (CProductoImpuesto r : this.productoImpuesto) {
      res = Double.valueOf(res.doubleValue() + r.getPorcentaje().doubleValue());
    }
    res = Double.valueOf(this.precio.doubleValue() / 100.0D * res.doubleValue());
    return res;
  }
  
  public Double getPrecioFinal() {
    return Double.valueOf(getPrecio().doubleValue() + getImpuestoTotal().doubleValue());
  }
  
  public Double getInventarioActual() {
    return Double.valueOf(this.entradas.doubleValue() - this.salidas.doubleValue());
  }

}
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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "c_inventario_movimientos_ajustes")
public class CInventarioAjuste {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private Long numeroAjuste = 0L;
  
  @Column(name = "tipo_movimiento", length = 1)
  private String tipoMovimiento;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "emisor_id")
  private Emisor emisor;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "user_id")
  private Usuario usuario;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "cinventario_movimiento_id")
  private List<CInventarioAjusteItems> items;
  
  @NotNull
  @Column(name = "fecha_emision_fe")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaEmisionFe;
  
  @NotNull
  @Column(name = "fecha_ingreso_sistema")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaIngresoSistema;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "moneda_id")
  private Moneda moneda;
  
  @Column(precision = 5)
  private Double tipoCambio;
  
  @Column(length = 255)
  private String observaciones;
  
  @Column(name = "total_serv_gravados", precision = 18)
  private double totalServGravados;
  
  @Column(name = "total_serv_exentos", precision = 18)
  private double totalServExentos;
  
  @Column(name = "total_serv_exonerado", precision = 18)
  private double totalServExonerado;
  
  @Column(name = "total_merc_gravadas", precision = 18)
  private double totalMercGravadas;
  
  @Column(name = "total_merc_exentas", precision = 18)
  private double totalMercExentas;
  
  @Column(name = "total_merc_exonerada", precision = 18)
  private double totalMercExonerada;
  
  @Column(name = "total_gravados", precision = 18)
  private double totalGravados;
  
  @Column(name = "total_exentos", precision = 18)
  private double totalExentos;
  
  @Column(name = "total_exonerado", precision = 18)
  private double totalExonerado;
  
  @Column(name = "total_ventas", precision = 18)
  private double totalVentas;
  
  @Column(name = "total_descuentos", precision = 18)
  private double totalDescuentos;
  
  @Column(name = "total_venta_neta", precision = 18)
  private double totalVentaNeta;
  
  @Column(name = "total_imp", precision = 18)
  private double totalImp;
  
  @Column(name = "total_iva_devuelto", precision = 18)
  private double totalIVADevuelto;
  
  @Column(name = "total_otros_cargos", length = 20)
  private double totalOtrosCargos;
  
  @Column(name = "total_comprobante", precision = 18)
  private double totalComprobante;
  
  public CInventarioAjuste() {
    this.items = new ArrayList<>();
  }
  
  public void addCInventarioAjusteItems(CInventarioAjusteItems item) {
    this.items.add(item);
  }

  public String getTipoMovimientoFinal() {
    String resp = "";
    if (this.tipoMovimiento.equalsIgnoreCase("E")) {
      resp = "Entrada de producto";
    } else {
      resp = "Salida de producto";
    } 
    return resp;
  }
}

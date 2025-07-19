package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "c_terminales_usuarios_acceso", uniqueConstraints = {@UniqueConstraint(columnNames = {"terminal_id", "usuario_id"})})
public class CTerminalUsuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "terminal_id")
  private CTerminal terminal;
  
  @NotNull
  @Column(name = "fecha_creacion")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaCreacion;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
  
  @Column(length = 1)
  private String status;
  
  @Column(name = "actividad_economica", length = 6)
  private String actividadEconomica;
  
  @Column(name = "tipo_de_documento", length = 5)
  private String tipoDeDocumento;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "moneda_id")
  @JsonBackReference
  private Moneda moneda;
  
  @Column(name = "incluir_omitir_receptor", length = 5)
  private String incluirOmitirReceptor;
  
  @Column(name = "tipo_busqueda_producto", length = 1)
  private String tipoBusquedaProducto;
  
  @Column(name = "aplica_descuento_facturacion")
  private Boolean aplicaDescuentoFacturacion;
  
  @Column(name = "modifica_precio_facturacion")
  private Boolean modificaPrecioFacturacion;
  
  @Column(name = "descuento_facturacion", precision = 18)
  private Double descuentoFacturacion = Double.valueOf(0.0D);
  
  @PrePersist
  public void prePersist() {
    this.fechaCreacion = new Date();
  }

  public String getLabelModificaPrecioFacturacion() {
    if (this.modificaPrecioFacturacion.booleanValue()) {
      return "Si";
    }
    return "No";
  }
  
  public String getLabelAplicaDescuentoFacturacion() {
    if (this.aplicaDescuentoFacturacion.booleanValue()) {
      return "Si";
    }
    return "No";
  }
}

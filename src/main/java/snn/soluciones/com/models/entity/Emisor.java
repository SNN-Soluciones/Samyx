package snn.soluciones.com.models.entity;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "emisores", uniqueConstraints = {@UniqueConstraint(columnNames = {"identificacion"})})
public class Emisor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_de_identificacion_id")
  private CTipoDeIdentificacion tipoDeIdentificacion;
  
  private String identificacion;
  
  @Column(name = "token_access")
  private String tokenAccess;
  
  @Column(length = 4)
  private String ambiente;
  
  @Column(name = "logo_empresa")
  private String logoEmpresa;
  
  @Column(name = "codigo_actividad", length = 6)
  public String codigoActividad;
  
  @Column(name = "nombre_razon_social", length = 100)
  private String nombreRazonSocial;
  
  @Column(name = "nombre_comercial", length = 80)
  private String nombreComercial;
  
  private String email;
  
  @Column(name = "codigo_pais", length = 3)
  private String codigoPais;
  
  @Column(length = 10)
  private String telefono;
  
  @Column(length = 10)
  private String fax;
  
  @Column(name = "detalle_en_factura1")
  private String detalleEnFactura1;
  
  @Column(name = "detalle_en_factura2")
  private String detalleEnFactura2;
  
  @Column(name = "nota_validez_proforma")
  private String notaValidezProforma;
  
  @Column(name = "nota_factura")
  private String nataFactura;
  
  private String observacion;
  
  @Column(name = "status_empresa")
  private Boolean statusEmpresa;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "provincia_id")
  private CProvincia provincia;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "canton_id")
  private CCanton canton;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "distrito_id")
  private CDistrito distrito;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "barrio_id")
  private CBarrio barrio;
  
  @Column(name = "otras_senas")
  private String otrasSenas;
  
  @Column(name = "certificado", length = 100)
  private String certificado;
  
  @Column(name = "fecha_emision_cert")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaEmisionCert;
  
  @Column(name = "fecha_caducidad_cert")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaCaducidadCert;
  
  @Column(name = "user_api", length = 100)
  private String userApi;
  
  @Column(name = "pw_api", length = 100)
  private String pwApi;
  
  @Column(name = "ping_api", length = 4)
  private String pingApi;
  
  @Column(name = "email_notificacion", length = 100)
  private String emailNotificacion;
  
  @Column(length = 1)
  private String impresion;
  
  @Column(name = "control_cajas", length = 1)
  private String controlCajas;
  
  @Column(name = "multimoneda_factura", length = 1)
  private String multiMonedaFactura;
  
  @Column(length = 1)
  private String decimales;
  
  @Column(length = 1)
  private String tipoCliente;
  
  @Column(length = 1)
  private String devolucionIva;
  
  @Column(length = 1)
  private String sistemaFarmacias;
  
  @NotNull
  @Column(name = "fecha_creacion")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaCreacion;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "emisor_id")
  private List<EmisorActividades> itemsActividades = new ArrayList<>();
  
  public void addActividadEmisor(EmisorActividades item) {
    this.itemsActividades.add(item);
  }

}
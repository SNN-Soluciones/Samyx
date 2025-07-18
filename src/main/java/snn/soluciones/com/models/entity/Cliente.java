package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "c_clientes", uniqueConstraints = {@UniqueConstraint(columnNames = {"emisor_id", "identificacion", "tipo_catalogo"})})
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  @JsonBackReference
  private Usuario usuario;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emisor_id")
  @JsonBackReference
  private Emisor emisor;
  
  @Column(name = "codigo_actividad", length = 6)
  private String codigoActividad;
  
  @Column(name = "tipo_catalogo", length = 1)
  private String tipoCatalogo;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_de_identificacion_id")
  private CTipoDeIdentificacion tipoDeIdentificacion;
  
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
  
  @Column(name = "otras_senas", length = 160)
  private String otrasSenas;
  
  @Column(length = 12)
  private String identificacion;
  
  @Column(name = "nombre_completo", length = 80)
  private String nombreCompleto;
  
  @Column(name = "codigo_pais", length = 3)
  private Integer codigoPais;
  
  @Column(length = 20)
  private String telefono1;
  
  @Column(length = 20)
  private String telefono2;
  
  @Column(length = 20)
  private String fax;
  
  @Column(length = 25)
  private String apartado;
  
  @Column(length = 100)
  private String web;
  
  @Column(length = 60)
  private String correo1;
  
  @Column(length = 60)
  private String correo2;
  
  @Column(length = 1)
  private String estadoCredito;
  
  @Column(name = "dias_credito", precision = 18, scale = 5)
  private Double diasCredito = Double.valueOf(0.0D);
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "tipo_documento_exoneracion_autorizacion_id")
  private CTipoDocumentoExoneracionOAutorizacion tipoDocumentoExoneracionOAutorizacion;
  
  @Column(length = 25)
  private String ley;
  
  @Column(name = "numero_documento", length = 17)
  private String numeroDocumento;
  
  @Column(name = "nombre_institucion", length = 100)
  private String nombreInstitucion;
  
  @Column(name = "porcentaje_exoneracion", length = 4, columnDefinition = "Double default 0.00")
  private Double porcentajeExoneracion;
  
  @Column(name = "fecha_inicio_exoneracion")
  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaInicioExoneracion;
  
  @Column(name = "fecha_fin_exoneracion")
  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern = "dd/MM/yyyy")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaFinExoneracion;
  
  public Long getTipoExoneracion() {
    if (this.tipoDocumentoExoneracionOAutorizacion != null) {
      return this.tipoDocumentoExoneracionOAutorizacion.getId();
    }
    return Long.valueOf(Long.parseLong("0"));
  }

  public String getVerificarFechaFinExoneracion() throws ParseException {
    String resp = "S";
    if (this.fechaFinExoneracion != null && this.fechaFinExoneracion.toString().length() > 0) {
      String pattern = "yyyy-MM-dd";
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      Date today = sdf.parse(sdf.format(new Date()));
      int res = getFechaFinExoneracion().compareTo(today);
      if (res > 0) {
        resp = "S";
      } else {
        resp = "N";
      } 
    } 
    return resp;
  }
}
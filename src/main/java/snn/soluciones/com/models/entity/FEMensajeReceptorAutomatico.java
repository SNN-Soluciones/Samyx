package snn.soluciones.com.models.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "fe_mensaje_receptor_automatico", uniqueConstraints = {@UniqueConstraint(columnNames = {"receptor_tipo_identificacion", "receptor_identificacion", "clave"})})
public class FEMensajeReceptorAutomatico {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "correo_from", length = 255)
  private String correoFrom;
  
  @Column(name = "emisor_factura", length = 160)
  private String emisorFactura;
  
  @Column(name = "emisor_tipo_identificacion", length = 2)
  private String emisorTipoIdentificacion;
  
  @Column(name = "emisor_identificacion", length = 20)
  private String emisorIdentificacion;
  
  @Column(name = "fecha_emision", length = 45)
  private String fechaEmision;
  
  @Column(length = 5)
  private String moneda;
  
  @Column(name = "tipo_cambio", length = 10)
  private String tipoCambio;
  
  @Column(name = "total_impuestos", length = 20)
  private String totalImpuestos;
  
  @Column(name = "total_comprobante", length = 20)
  private String totalComprobante;
  
  @Column(name = "receptor_tipo_identificacion", length = 2)
  private String receptorTipoIdentificacion;
  
  @Column(name = "receptor_identificacion", length = 25)
  private String receptorIdentificacion;
  
  @Column(length = 50)
  private String clave;
  
  @Column(name = "factura_xml", length = 250)
  private String facturaXml;
  
  @Column(name = "factura_pdf", length = 250)
  private String facturaPdf;
  
  @NotNull
  @Column(name = "fecha_creacion")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fechaCreacion;
  
  @Column(length = 1)
  private String estado;

}

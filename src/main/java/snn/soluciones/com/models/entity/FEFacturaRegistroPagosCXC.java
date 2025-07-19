package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.SimpleDateFormat;
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
@Table(name = "fe_factura_registro_pagos_cxc")
public class FEFacturaRegistroPagosCXC {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "numero_cxc")
  private Long numeroCXC;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "factura_cxc_id")
  private FEFacturasCXC facturaCXC;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "emisor_id")
  @JsonIgnore
  private Emisor emisor;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "cliente_id")
  @JsonIgnore
  private Cliente cliente;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private Usuario usuario;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "medio_de_pago")
  private CMedioDePago medioDePago;
  
  @NotNull
  @Column(name = "fecha_registro_pago")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  private Date fechaRegistroAbondo;
  
  @Column(name = "fecha_pago_abono")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @JsonFormat(pattern = "dd/MM/yyyy")
  private Date fechaPagoAbondo;
  
  @Column(name = "monto_abondo", precision = 18, columnDefinition = "Double default 0.00")
  private double montoAbono;
  
  @Column(name = "monto_actual", precision = 18, columnDefinition = "Double default 0.00")
  private double montoActual;
  
  @Column(name = "numero_tarjeta", length = 4)
  private String numeroTarjeta;
  
  @Column(name = "numero_autorizacion", length = 50)
  private String numeroAutorizacion;
  
  @Column(name = "num_documento", length = 100)
  private String numDocumento;
  
  @Column(length = 255)
  private String observacion;

  public String getFechaAbono() {
    String r = "";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    r = sdf.format(this.fechaRegistroAbondo);
    return r;
  }

}

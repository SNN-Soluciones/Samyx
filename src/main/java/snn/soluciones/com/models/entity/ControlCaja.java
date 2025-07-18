package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "control_cajas")
public class ControlCaja {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  @JsonBackReference
  private Usuario usuario;
  
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_cerro_id")
  @JsonBackReference
  private Usuario usuarioCerro;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emisor_id")
  private Emisor emisor;
  
  @NotNull
  @Column(name = "fecha_apertura")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  private Date fechaApertura;
  
  @Column(name = "fecha_cierre")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  private Date fechaCierre;
  
  @Column(name = "apertura_colones", precision = 18, scale = 5)
  private double aperturaColones = 0.0D;
  
  @Column(name = "apertura_dolares", precision = 18, scale = 5)
  private double aperturaDolares = 0.0D;
  
  @Column(name = "apertura_euros", precision = 18, scale = 5)
  private double aperturaEuros = 0.0D;
  
  @Column(name = "salidas_colones", precision = 18, scale = 5)
  private double salidasColones = 0.0D;
  
  @Column(name = "salidas_dolares", precision = 18, scale = 5)
  private double salidasDolares = 0.0D;
  
  @Column(name = "salidas_euros", precision = 18, scale = 5)
  private double salidasEuros = 0.0D;
  
  @Column(name = "entradas_colones", precision = 18, scale = 5)
  private double entradasColones = 0.0D;
  
  @Column(name = "entradas_dolares", precision = 18, scale = 5)
  private double entradasDolares = 0.0D;
  
  @Column(name = "entradas_euros", precision = 18, scale = 5)
  private double entradasEuros = 0.0D;
  
  @Column(name = "ventas_colones_efectivo", precision = 18, scale = 5)
  private double ventasColonesEfectivo = 0.0D;
  
  @Column(name = "ventas_dolares_efectivo", precision = 18, scale = 5)
  private double ventasDolaresEfectivo = 0.0D;
  
  @Column(name = "ventas_euros_efectivo", precision = 18, scale = 5)
  private double ventasEurosEfectivo = 0.0D;
  
  @Column(name = "ventas_colones_tarjeta", precision = 18, scale = 5)
  private double ventasColonesTarjeta = 0.0D;
  
  @Column(name = "ventas_dolares_tarjeta", precision = 18, scale = 5)
  private double ventasDolaresTarjeta = 0.0D;
  
  @Column(name = "ventas_eurostarjeta", precision = 18, scale = 5)
  private double ventasEurosTarjeta = 0.0D;
  
  @Column(name = "ventas_colones_credito", precision = 18, scale = 5)
  private double ventasColonesCredito = 0.0D;
  
  @Column(name = "ventas_dolares_credito", precision = 18, scale = 5)
  private double ventasDolaresCredito = 0.0D;
  
  @Column(name = "ventas_euros_credito", precision = 18, scale = 5)
  private double ventasEurosCredito = 0.0D;
  
  @Column(name = "ventas_colones_cheques", precision = 18, scale = 5)
  private double ventasColonesCheques = 0.0D;
  
  @Column(name = "ventas_dolares_cheques", precision = 18, scale = 5)
  private double ventasDolaresCheques = 0.0D;
  
  @Column(name = "ventas_euros_cheques", precision = 18, scale = 5)
  private double ventasEurosCheques = 0.0D;
  
  @Column(name = "ventas_colones_deposito", precision = 18, scale = 5)
  private double ventasColonesDeposito = 0.0D;
  
  @Column(name = "ventas_dolares_deposito", precision = 18, scale = 5)
  private double ventasDolaresDeposito = 0.0D;
  
  @Column(name = "ventas_euros_deposito", precision = 18, scale = 5)
  private double ventasEurosDeposito = 0.0D;
  
  @Column(name = "cxc_colones_efectivo", precision = 18, scale = 5)
  private double cxcColonesEfectivo = 0.0D;
  
  @Column(name = "cxc_dolares_efectivo", precision = 18, scale = 5)
  private double cxcDolaresEfectivo = 0.0D;
  
  @Column(name = "cxc_euros_efectivo", precision = 18, scale = 5)
  private double cxcEurosEfectivo = 0.0D;
  
  @Column(name = "cxc_colones_tarjeta", precision = 18, scale = 5)
  private double cxcColonesTarjeta = 0.0D;
  
  @Column(name = "cxc_dolares_tarjeta", precision = 18, scale = 5)
  private double cxcDolaresTarjeta = 0.0D;
  
  @Column(name = "cxc_euros_tarjeta", precision = 18, scale = 5)
  private double cxcEurosTarjeta = 0.0D;
  
  @Column(name = "cxc_colones_deposito", precision = 18, scale = 5)
  private double cxcColonesDeposito = 0.0D;
  
  @Column(name = "cxc_dolares_deposito", precision = 18, scale = 5)
  private double cxcDolaresDeposito = 0.0D;
  
  @Column(name = "cxc_euros_deposito", precision = 18, scale = 5)
  private double cxcEurosDeposito = 0.0D;
  
  @Column(name = "cxc_colones_cheques", precision = 18, scale = 5)
  private double cxcColonesCheques = 0.0D;
  
  @Column(name = "cxc_dolares_cheques", precision = 18, scale = 5)
  private double cxcDolaresCheques = 0.0D;
  
  @Column(name = "cxc_euros_cheques", precision = 18, scale = 5)
  private double cxcEurosCheques = 0.0D;
  
  @Column(name = "cierre_colones", precision = 18, scale = 5)
  private double cierreColones = 0.0D;
  
  @Column(name = "cierre_dolares", precision = 18, scale = 5)
  private double cierreDolares = 0.0D;
  
  @Column(name = "cierre_euros", precision = 18, scale = 5)
  private double cierreEuros = 0.0D;
  
  @Column(name = "totalEfectivoColones", precision = 18, scale = 5)
  private Double totalEfectivoColones = Double.valueOf(0.0D);
  
  @Column(name = "totalEfectivoDolares", precision = 18, scale = 5)
  private Double totalEfectivoDolares = Double.valueOf(0.0D);
  
  @Column(name = "totalEfectivoEuros", precision = 18, scale = 5)
  private Double totalEfectivoEuros = Double.valueOf(0.0D);
  
  @Column(name = "totalTarjetaColones", precision = 18, scale = 5)
  private Double totalTarjetaColones = Double.valueOf(0.0D);
  
  @Column(name = "totalTarjetaDolares", precision = 18, scale = 5)
  private Double totalTarjetaDolares = Double.valueOf(0.0D);
  
  @Column(name = "totalTarjetaEuros", precision = 18, scale = 5)
  private Double totalTarjetaEuros = Double.valueOf(0.0D);
  
  @Column(name = "totalChequeColones", precision = 18, scale = 5)
  private Double totalChequeColones = Double.valueOf(0.0D);
  
  @Column(name = "totalChequeDolares", precision = 18, scale = 5)
  private Double totalChequeDolares = Double.valueOf(0.0D);
  
  @Column(name = "totalChequeEuros", precision = 18, scale = 5)
  private Double totalChequeEuros = Double.valueOf(0.0D);
  
  @Column(name = "totalDepositosColones", precision = 18, scale = 5)
  private Double totalDepositosColones = Double.valueOf(0.0D);
  
  @Column(name = "totalDepositosDolares", precision = 18, scale = 5)
  private Double totalDepositosDolares = Double.valueOf(0.0D);
  
  @Column(name = "totalDepositosEuros", precision = 18, scale = 5)
  private Double totalDepositosEuros = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalEfectivoColones", precision = 18, scale = 5)
  private Double cierreTotalEfectivoColones = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalEfectivoDolares", precision = 18, scale = 5)
  private Double cierreTotalEfectivoDolares = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalEfectivoEuros", precision = 18, scale = 5)
  private Double cierreTotalEfectivoEuros = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalTarjetaColones", precision = 18, scale = 5)
  private Double cierreTotalTarjetaColones = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalTarjetaDolares", precision = 18, scale = 5)
  private Double cierreTotalTarjetaDolares = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalTarjetaEuros", precision = 18, scale = 5)
  private Double cierreTotalTarjetaEuros = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalChequeColones", precision = 18, scale = 5)
  private Double cierreTotalChequeColones = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalChequeDolares", precision = 18, scale = 5)
  private Double cierreTotalChequeDolares = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalChequeEuros", precision = 18, scale = 5)
  private Double cierreTotalChequeEuros = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalDepositosColones", precision = 18, scale = 5)
  private Double cierreTotalDepositosColones = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalDepositosDolares", precision = 18, scale = 5)
  private Double cierreTotalDepositosDolares = Double.valueOf(0.0D);
  
  @Column(name = "cierreTotalDepositosEuros", precision = 18, scale = 5)
  private Double cierreTotalDepositosEuros = Double.valueOf(0.0D);
  
  @Column(name = "totalDevolucionesColones", precision = 18, scale = 5)
  private Double totalDevolucionesColones = Double.valueOf(0.0D);
  
  @Column(name = "totalDevolucionesDolares", precision = 18, scale = 5)
  private Double totalDevolucionesDolares = Double.valueOf(0.0D);
  
  @Column(name = "totalDevolucionesEuros", precision = 18, scale = 5)
  private Double totalDevolucionesEuros = Double.valueOf(0.0D);
  
  @Column(name = "estado_caja", length = 1)
  private String estadoCaja;
  
  @Column(length = 500)
  private String observaciones;

}
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
@Table(name = "fe_facturas")
public class FEFactura {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "numero_factura")
  private Long numeroFactura;
  
  @Column(name = "codigo_actividad_emisor", length = 6)
  private String codigoActividadEmisor;
  
  @Column(length = 1)
  private String estado;
  
  @Column(name = "receta_medico", length = 1)
  private String recetaMedico;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "emisor_id")
  private Emisor emisor;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "cliente_id")
  private Cliente cliente;
  
  @Column(name = "cliente_contado", length = 160)
  private String clienteContado = "";
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "user_id")
  private Usuario usuario;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "factura_id")
  private List<FEFacturaItem> items;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "factura_id")
  private List<FEFacturaReferencia> itemsReferencias;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "factura_id")
  private List<FEBitacora> itemsBitatora;
  
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
  @JoinColumn(name = "factura_id")
  private List<FEFacturaOtrosCargos> itemsOtrosCargos;
  
  @Column(length = 50)
  private String clave;
  
  @Column(length = 20)
  private String consecutivo;
  
  @Column(name = "fecha_emision", length = 30)
  private String fechaEmision;
  
  @NotNull
  @Column(name = "fecha_limite")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  private Date fechaEmisionFe;
  
  @Column(name = "tipo_documento", length = 4)
  private String tipoDocumento;
  
  @Column(length = 10)
  private String situacion;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "sucursal_id")
  private CSucursal sucursal;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "terminal_id")
  private CTerminal terminal;
  
  @Column(name = "cond_venta", length = 2)
  private String condVenta;
  
  @Column(name = "plazoCredito", length = 5)
  private String plazoCredito;
  
  @Column(name = "medioPago", length = 2)
  private String medioPago;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "moneda_id")
  private Moneda moneda;
  
  @Column(name = "tipoCambio", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double tipoCambio;
  
  @Column(name = "tipo_cambio_dolar", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double tipoCambioDolar;
  
  @Column(name = "tipo_cambio_euro", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double tipoCambioEuro;
  
  @Column(name = "total_serv_gravados", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalServGravados;
  
  @Column(name = "total_serv_exentos", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalServExentos;
  
  @Column(name = "total_serv_exonerado", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalServExonerado;
  
  @Column(name = "total_merc_gravadas", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalMercGravadas;
  
  @Column(name = "total_merc_exentas", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalMercExentas;
  
  @Column(name = "total_merc_exonerada", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalMercExonerada;
  
  @Column(name = "total_gravados", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalGravados;
  
  @Column(name = "total_exentos", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalExentos;
  
  @Column(name = "total_exonerado", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalExonerado;
  
  @Column(name = "total_ventas", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalVentas;
  
  @Column(name = "total_descuentos", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalDescuentos;
  
  @Column(name = "total_venta_neta", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalVentaNeta;
  
  @Column(name = "total_imp", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalImp;
  
  @Column(name = "total_iva_devuelto", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalIVADevuelto;
  
  @Column(name = "total_otros_cargos", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalOtrosCargos;
  
  @Column(name = "total_comprobante", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double totalComprobante;
  
  @Column(name = "otros", columnDefinition = "TEXT")
  private String otros;
  
  @Column(name = "pago_con", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double pagoCon;
  
  @Column(precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double efectivo;
  
  @Column(precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double tarjeta;
  
  @Column(name = "numero_tarjeta", length = 4)
  private String numeroTarjeta;
  
  @Column(name = "numero_autorizacion", length = 50)
  private String numeroAutorizacion;
  
  @Column(precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double cheque;
  
  @Column(name = "transferencia", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double transferencia;
  
  @Column(name = "numero_deposito_transferencia", length = 100)
  private String numeroDepositoTransferencia;
  
  @Column(name = "su_vueltro", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double suVueltro;
  
  @Column(name = "efectivo_dolares", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double efectivoDolares;
  
  @Column(name = "tarjeta_dolares", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double tarjetaDolares;
  
  @Column(name = "numero_tarjeta_dolares", length = 4)
  private String numeroTarjetaDolares;
  
  @Column(name = "numero_autorizacion_dolares", length = 50)
  private String numeroAutorizacionDolares;
  
  @Column(name = "cheque_dolares", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double chequeDolares;
  
  @Column(name = "transferencia_dolares", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double transferenciaDolares;
  
  @Column(name = "numero_deposito_transferencia_dolares", length = 100)
  private String numeroDepositoTransferenciaDolares;
  
  @Column(name = "su_vuelto_dolares", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double suVueltoDolares;
  
  @Column(name = "efectivo_euros", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double efectivoEuros;
  
  @Column(name = "tarjeta_euros", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double tarjetaEuros;
  
  @Column(name = "numero_tarjeta_euros", length = 4)
  private String numeroTarjetaEuros;
  
  @Column(name = "numero_autorizacion_euros", length = 50)
  private String numeroAutorizacionEuros;
  
  @Column(name = "cheque_euros", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double chequeEuros;
  
  @Column(name = "transferencia_euros", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double transferenciaEuros;
  
  @Column(name = "numero_deposito_transferencia_euros", length = 100)
  private String numeroDepositoTransferenciaEuros;
  
  @Column(name = "su_vuelto_euros", precision = 18, columnDefinition = "Decimal(18,5) default '0.00'")
  private Double suVueltoEuros;
  
  @Column(name = "tipo_tarjeta", length = 1)
  private String tipoTarjeta;
  
  @Column(name = "observacion_pago_factura")
  private String observacionPagoFactura;
  
  @Column(name = "monto_exento", precision = 18, columnDefinition = "Double default 0.00")
  private Double montoExento;
  
  @Column(precision = 18, columnDefinition = "Double default 0.00")
  private Double exento0;
  
  @Column(name = "iva_reducida1", precision = 18, columnDefinition = "Double default 0.00")
  private Double ivaReducida1;
  
  @Column(name = "iva_reducida2", precision = 18, columnDefinition = "Double default 0.00")
  private Double ivaReducida2;
  
  @Column(name = "iva_reducida4", precision = 18, columnDefinition = "Double default 0.00")
  private Double ivaReducida4;
  
  @Column(name = "ivatransitorio0", precision = 18, columnDefinition = "Double default 0.00")
  private Double ivatransitorio0;
  
  @Column(name = "ivatransitorio4", precision = 18, columnDefinition = "Double default 0.00")
  private Double ivatransitorio4;
  
  @Column(name = "ivatransitorio8", precision = 18, columnDefinition = "Double default 0.00")
  private Double ivatransitorio8;
  
  @Column(name = "ivageneral13", precision = 18, columnDefinition = "Double default 0.00")
  private Double ivageneral13;
  
  @Column(name = "montoIvaReducida1", precision = 18, columnDefinition = "Double default 0.00")
  private Double montoIvaReducida1;
  
  @Column(name = "montoIvaReducida2", precision = 18, columnDefinition = "Double default 0.00")
  private Double montoIvaReducida2;
  
  @Column(name = "montoIvaReducida4", precision = 18, columnDefinition = "Double default 0.00")
  private Double montoIvaReducida4;
  
  @Column(name = "montoIvatransitorio0", precision = 18, columnDefinition = "Double default 0.00")
  private Double montoIvatransitorio0;
  
  @Column(name = "montoIvatransitorio4", precision = 18, columnDefinition = "Double default 0.00")
  private Double montoIvatransitorio4;
  
  @Column(name = "montoIvatransitorio8", precision = 18, columnDefinition = "Double default 0.00")
  private Double montoIvatransitorio8;
  
  @Column(name = "montoIvageneral13", precision = 18, columnDefinition = "Double default 0.00")
  private Double monto_ivageneral13;
  
  public FEFactura() {
    this
      .efectivo = Double.valueOf(0.0D);
    this
      .tarjeta = Double.valueOf(0.0D);
    this
      .cheque = Double.valueOf(0.0D);
    this
      .transferencia = Double.valueOf(0.0D);
    this
      .efectivoDolares = Double.valueOf(0.0D);
    this
      .tarjetaDolares = Double.valueOf(0.0D);
    this
      .chequeDolares = Double.valueOf(0.0D);
    this
      .transferenciaDolares = Double.valueOf(0.0D);
    this
      .efectivoEuros = Double.valueOf(0.0D);
    this
      .tarjetaEuros = Double.valueOf(0.0D);
    this
      .chequeEuros = Double.valueOf(0.0D);
    this
      .transferenciaEuros = Double.valueOf(0.0D);
    this.items = new ArrayList<>();
    this.itemsReferencias = new ArrayList<>();
    this.itemsBitatora = new ArrayList<>();
    this.itemsOtrosCargos = new ArrayList<>();
  }

  public void addFEItemFactura(FEFacturaItem item) {
    this.items.add(item);
  }
  
  public void addReferenciaFactura(FEFacturaReferencia referencia) {
    this.itemsReferencias.add(referencia);
  }
  
  public void addDocumentoABitacora(FEBitacora bitacora) {
    this.itemsBitatora.add(bitacora);
  }
  
  public void addOtrosCargos(FEFacturaOtrosCargos otrosCargos) {
    this.itemsOtrosCargos.add(otrosCargos);
  }

  public String getEstadoMH() {
    String resp = "";
    for (FEBitacora b : this.itemsBitatora) {
      resp = b.getEstadoHacienda();
    }
    return resp;
  }

  public String getRespTipoDocumento() {
    String resp = "";
    String td = this.tipoDocumento;
    switch (td) {
      case "FE":
        resp = "Factura Electrónica";
        break;
      case "ND":
        resp = "Nota de debito Electrónica";
        break;
      case "NC":
        resp = "Nota de crédito Electrónica";
        break;
      case "TE":
        resp = "Tiquete Electrónico";
        break;
      case "FEC":
        resp = "Factura Electrónica de Compra";
        break;
      case "FEE":
        resp = "Factura Electrónica Exportación";
        break;
      case "PR":
        resp = "Proforma";
        break;
    } 
    return resp;
  }
}
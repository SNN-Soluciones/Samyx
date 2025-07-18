package snn.soluciones.com.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "historico_otros_sistemas")
public class HistoricoOtrosSistemas {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emisor_id")
  private Emisor emisor;
  
  @Column(length = 20)
  private String consecutivo;
  
  @Column(length = 50)
  private String clave;
  
  @Column(length = 20)
  private String factura;
  
  @Column(length = 6, name = "actividad_economica")
  private String actividadEconomica;
  
  @Column(name = "tipo_documento", length = 50)
  private String tipoDocumento;
  
  @Column(name = "fecha_emision", length = 50)
  private String fechaEmision;
  
  @Column(name = "nombre_receptor", length = 160)
  private String nombreReceptor;
  
  @Column(name = "tipo_cedula_receptor", length = 2)
  private String tipoCedulaReceptor;
  
  @Column(name = "cedula_receptor", length = 20)
  private String cedulaReceptor;
  
  @Column(name = "condicion_venta", length = 50)
  private String condicionVenta;
  
  @Column(name = "estado_hacienda", length = 50)
  private String estadoHacienda;
  
  @Column(length = 20)
  private String moneda;
  
  @Column(name = "medio_pago", length = 50)
  private String medioPago;
  
  @Column(name = "tipo_cambio", length = 20)
  private String tipoCambio;
  
  @Column(name = "total_ser_exentos", length = 20)
  private String totalSerExentos;
  
  @Column(name = "total_ser_gravados", length = 20)
  private String TotalSerGravados;
  
  @Column(name = "total_ser_exonerados", length = 20)
  private String totalSerExonerados;
  
  @Column(name = "total_mer_exentas", length = 20)
  private String totalMerExentas;
  
  @Column(name = "total_mer_gravadas", length = 20)
  private String totalMerGravadas;
  
  @Column(name = "total_mer_exoneradas", length = 20)
  private String totalMerExoneradas;
  
  @Column(name = "total_exento", length = 20)
  private String totalExento;
  
  @Column(name = "total_gravado", length = 20)
  private String totalGravado;
  
  @Column(name = "total_exonerado", length = 20)
  private String totalExonerado;
  
  @Column(name = "total_venta", length = 20)
  private String totalVenta;
  
  @Column(name = "total_descuentos", length = 20)
  private String totalDescuentos;
  
  @Column(name = "total_venta_neta", length = 20)
  private String totalVentaNeta;
  
  @Column(name = "total_impuesto", length = 20)
  private String totalImpuesto;
  
  @Column(name = "total_iva_devuelto", length = 20)
  private String totalIVADevuelto;
  
  @Column(name = "total_otros_cargos", length = 20)
  private String totalOtrosCargos;
  
  @Column(name = "total_comprobante", length = 20)
  private String totalComprobante;

}

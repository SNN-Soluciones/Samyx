package snn.soluciones.com.models.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "fe_bitacora")
public class FEBitacora {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  private FEFactura factura;
  
  @Column(length = 50)
  private String clave;
  
  @Column(name = "tipo_documento", length = 5)
  private String tipoDocumento;
  
  @Column(length = 3)
  private int responseCode;
  
  @Column(name = "name_xml_enviado")
  private String nameXmlEnviado;
  
  @Column(name = "name_xml_respuesta")
  private String nameXmlRespuesta;
  
  @Column(name = "fecha_emision", length = 30)
  private String fechaEmision;
  
  @Column(name = "estado_hacienda", length = 15)
  private String estadoHacienda;
  
  @Column(name = "fecha_aceptacion", length = 30)
  private String fechaAceptacion;

}
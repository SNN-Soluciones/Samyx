package snn.soluciones.com.models.entity;

import javax.persistence.CascadeType;
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
@Table(name = "fe_facturas_referencias")
public class FEFacturaReferencia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(length = 2)
  private String tipoDoc;
  
  @Column(length = 50)
  private String numero;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "codigo_de_referencia_id")
  private CCodigoReferencia codigoReferencia;
  
  @Column(length = 25)
  private String fechaEmision;
  
  @Column(length = 180)
  private String razon;

}
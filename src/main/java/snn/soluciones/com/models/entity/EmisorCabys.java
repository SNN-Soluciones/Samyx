package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "emisores_cabys", uniqueConstraints = {@UniqueConstraint(columnNames = {"emisor_id", "codigo_hacienda"})})
public class EmisorCabys {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  private Emisor emisor;
  
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  private CCabys cabys;
  
  @Column(name = "codigo_hacienda", length = 13)
  private String codigoHacienda;
  
  @Column(name = "producto_servicio")
  private String productoServicio;
  
  @Column(length = 100)
  private String impuesto;

}
package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
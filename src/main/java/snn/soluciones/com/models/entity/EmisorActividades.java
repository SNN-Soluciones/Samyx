package snn.soluciones.com.models.entity;

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
@Table(name = "emisores_actividades", uniqueConstraints = {@UniqueConstraint(columnNames = {"emisor_id", "codigo_actividad_emisor"})})
public class EmisorActividades {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Emisor emisor;
  
  @Column(name = "codigo_actividad_emisor", length = 6)
  private String codigoActividadEmisor;
  
  @Column(name = "detalle_actividad", length = 100)
  private String detalleActividad;
  
  @Column(length = 50)
  private String estado;

  public String toString() {
    return "EmisorActividades [id=" + this.id + ", emisor=" + this.emisor + ", codigoActividad=" + this.codigoActividadEmisor + ", detalleActividad=" + this.detalleActividad + ", estado=" + this.estado + "]";
  }
}
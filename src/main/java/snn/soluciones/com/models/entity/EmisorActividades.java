package snn.soluciones.com.models.entity;

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
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
@Table(name = "control_cajas_movimientos")
public class ControlCajaMovimientos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  @JsonBackReference
  private Usuario usuario;
  
  @NotNull
  @Column(name = "fecha_movimiento")
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
  private Date fechaMovimiento;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "caja_id")
  @JsonBackReference
  private ControlCaja controlCaja;
  
  @Column(name = "tipo_movimiento", length = 1)
  private String tipoMovimiento;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "moneda_id")
  @JsonBackReference
  private Moneda moneda;
  
  @Column(name = "monto_movimiento", precision = 18, scale = 5)
  private double montoMovimiento;
  
  @Column(length = 255)
  private String detalle;

}
package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  
  @Column(name = "monto_movimiento", precision = 18)
  private double montoMovimiento;
  
  @Column(length = 255)
  private String detalle;

}
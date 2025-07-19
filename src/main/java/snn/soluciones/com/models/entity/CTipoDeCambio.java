package snn.soluciones.com.models.entity;

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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Entity
@Table(name = "c_tipo_de_cambio", uniqueConstraints = {@UniqueConstraint(columnNames = {"moneda_id", "fecha"})})
public class CTipoDeCambio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "moneda_id")
  private Moneda moneda;
  
  @NotNull
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date fecha;
  
  @Column(name = "tipo_de_cambio")
  private Double tipoDeCambio;
  
  @Column(name = "tipo_cambio_dolar_euro", precision = 18)
  private Double tipoCambioDolarEuro;

}

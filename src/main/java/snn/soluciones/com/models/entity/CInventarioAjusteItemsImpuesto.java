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
@Table(name = "c_inventario_movimientos_impuestos_ajustes")
public class CInventarioAjusteItemsImpuesto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @JoinColumn(name = "impuesto_id")
  private CImpuesto impuesto;
  
  @Column(precision = 20, scale = 3)
  private Double tarifa;
  
  @Column(precision = 20, scale = 3)
  private Double monto;

}

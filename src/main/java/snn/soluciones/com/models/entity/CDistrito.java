package snn.soluciones.com.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "c_distritos", uniqueConstraints = {@UniqueConstraint(columnNames = {"canton_id", "distrito", "numero_distrito"})})
public class CDistrito {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "canton_id")
  private CCanton canton;
  
  @Column(name = "numero_distrito", length = 3)
  private String numeroDistrito;
  
  @Column(length = 50)
  private String distrito;

}

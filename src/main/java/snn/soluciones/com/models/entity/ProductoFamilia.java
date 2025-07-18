package snn.soluciones.com.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "productos_familias")
public class ProductoFamilia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id")
  @JsonBackReference
  private Usuario usuario;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "emisor_id")
  @JsonBackReference
  private Emisor emisor;
  
  @Column(name = "nombre_familia", length = 100, unique = true)
  private String nombreFamilia;

}
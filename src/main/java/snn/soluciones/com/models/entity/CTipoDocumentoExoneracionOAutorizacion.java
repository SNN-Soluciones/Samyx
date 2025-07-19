package snn.soluciones.com.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "c_tipo_documento_de_exoneracion_o_autorizacion", uniqueConstraints = {@UniqueConstraint(columnNames = {"exoneracion_o_autorizacion"})})
public class CTipoDocumentoExoneracionOAutorizacion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "exoneracion_o_autorizacion", length = 50)
  private String exoneracionOAutorizacion;

}

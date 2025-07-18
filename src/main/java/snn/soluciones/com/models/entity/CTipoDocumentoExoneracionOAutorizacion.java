package snn.soluciones.com.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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

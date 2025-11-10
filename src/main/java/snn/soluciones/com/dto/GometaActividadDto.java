package snn.soluciones.com.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GometaActividadDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("codigo")
    private String codigo;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("fechaCreacion")
    private String fechaCreacion;

    @JsonProperty("activo")
    private Boolean activo;
}
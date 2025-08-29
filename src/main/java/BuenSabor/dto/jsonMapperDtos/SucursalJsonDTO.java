package BuenSabor.dto.jsonMapperDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SucursalJsonDTO {

    @JsonProperty("fecha_alta")
    private String fechaAlta;

    @JsonProperty("fecha_baja")
    private String fechaBaja;

    @JsonProperty("horario_apertura")
    private String horarioApertura;

    @JsonProperty("horario_cierre")
    private String horarioCierre;

    private String nombre;

    @JsonProperty("domicilio_id")
    private Integer domicilioId;

    @JsonProperty("empresa_id")
    private Integer empresaId;
}

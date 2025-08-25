package BuenSabor.dto.jsonMapperDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DomicilioJsonDTO {

    private String calle;
    private Integer cp;
    private Integer numero;

    @JsonProperty("localidad_id")
    private Integer localidadId;
}

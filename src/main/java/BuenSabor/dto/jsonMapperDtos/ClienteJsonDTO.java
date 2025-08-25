package BuenSabor.dto.jsonMapperDtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteJsonDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String username;
    private String password;

    @JsonProperty("domicilio_id")
    private Long domicilioId;
}

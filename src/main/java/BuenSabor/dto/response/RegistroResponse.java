package BuenSabor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistroResponse {
    private String mensaje;
    private Long usuarioId;
}
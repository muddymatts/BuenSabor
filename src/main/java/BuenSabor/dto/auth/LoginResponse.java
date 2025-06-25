package BuenSabor.dto.auth;

import BuenSabor.dto.usuario.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UsuarioDTO usuario;
}
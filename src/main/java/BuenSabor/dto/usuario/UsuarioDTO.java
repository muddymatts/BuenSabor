package BuenSabor.dto.usuario;

import BuenSabor.enums.RolEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO {

    private boolean estaActivo;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String rol;

    private EmpleadoDTO empleado;
    private ClienteDTO cliente;

    @Data
    @AllArgsConstructor
    public static class EmpleadoDTO {
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
        private RolEnum rol;
    }

    @Data
    @AllArgsConstructor
    public static class ClienteDTO {
        private Long id;
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
    }
}
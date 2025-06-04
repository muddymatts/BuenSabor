package BuenSabor.dto.usuario;

import BuenSabor.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
        private Rol rol;
    }

    @Data
    @AllArgsConstructor
    public static class ClienteDTO {
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
    }
}
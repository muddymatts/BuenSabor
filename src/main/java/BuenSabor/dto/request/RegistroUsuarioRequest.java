package BuenSabor.dto.request;

import lombok.Data;

@Data
public class RegistroUsuarioRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private RegistroDireccion direccion;
    private String username;
    private String password;

    @Data
    public static class RegistroDireccion {
        private String calle;
        private int numeroCalle;
        private int codigoPostal;
        private int localidadId;
    }
}
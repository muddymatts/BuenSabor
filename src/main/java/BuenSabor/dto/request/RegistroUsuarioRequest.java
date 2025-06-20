package BuenSabor.dto.request;

import lombok.Data;

@Data
public class RegistroUsuarioRequest {
    private String nombre;
    private String apellido;
    private String email;
    private int paisId;
    private int provinciaId;
    private int localidadId;
    private RegistroDireccion direccion;
    private String telefono;
    private String username;
    private String password;

    @Data
    public static class RegistroDireccion {
        private String calle;
        private int numeroCalle;
        private int codigoPostal;
    }
}
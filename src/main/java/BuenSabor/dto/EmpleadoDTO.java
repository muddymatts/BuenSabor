package BuenSabor.dto;

import BuenSabor.enums.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpleadoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private RolEnum rol;
    private String username;
    private boolean estaActivo;
}
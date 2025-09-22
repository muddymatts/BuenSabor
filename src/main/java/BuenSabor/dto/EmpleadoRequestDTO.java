package BuenSabor.dto;

import BuenSabor.enums.RolEnum;
import lombok.Data;

@Data
public class EmpleadoRequestDTO {
    private Long id; // para editar
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private RolEnum rol;
    private String username; // para crear
    private String password; // opcional al crear
}
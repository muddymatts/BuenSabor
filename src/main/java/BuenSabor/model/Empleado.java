package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Empleado {

    @Id
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}

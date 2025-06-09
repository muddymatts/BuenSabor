package BuenSabor.model;

import BuenSabor.enums.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Empleado extends EntityBean {

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    @Enumerated(EnumType.STRING)
    private Rol rol;
}

package BuenSabor.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente extends EntityApp {

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    // Relación 1-1 con Domicilio
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

}

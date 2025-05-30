package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario extends EntityApp {

    private String auth0Id;
    private String username;

    // Relación 1-1 con Cliente
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relación 1-1 con Empleado
    @OneToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
}

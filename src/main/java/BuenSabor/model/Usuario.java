package BuenSabor.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends EntityApp {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @Column(name = "esta_activo", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean estaActivo = true;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
}

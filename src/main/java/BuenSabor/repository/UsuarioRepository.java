package BuenSabor.repository;

import BuenSabor.model.Empleado;
import BuenSabor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByEmpleado(Empleado empleado);

    List<Usuario> findByEmpleadoIn(List<Empleado> empleados);

}

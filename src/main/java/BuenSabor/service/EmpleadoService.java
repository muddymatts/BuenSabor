package BuenSabor.service;

import BuenSabor.dto.EmpleadoDTO;
import BuenSabor.model.Empleado;
import BuenSabor.model.Usuario;
import BuenSabor.repository.EmpleadoRepository;
import BuenSabor.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<EmpleadoDTO> listarEmpleadosConUsuarios() {
        List<Empleado> empleados = empleadoRepository.findAll();

        return empleados.stream()
                .map(empleado -> {
                    Usuario usuario = usuarioRepository.findByEmpleado(empleado).orElse(null);

                    return new EmpleadoDTO(
                            empleado.getId(),
                            empleado.getNombre(),
                            empleado.getApellido(),
                            empleado.getTelefono(),
                            empleado.getEmail(),
                            empleado.getRol(),
                            usuario != null ? usuario.getUsername() : null,
                            usuario != null && usuario.isEstaActivo()
                    );
                })
                .collect(Collectors.toList());
    }
}

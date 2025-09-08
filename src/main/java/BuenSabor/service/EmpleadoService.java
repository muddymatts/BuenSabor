package BuenSabor.service;

import BuenSabor.dto.EmpleadoDTO;
import BuenSabor.dto.EmpleadoRequestDTO;
import BuenSabor.enums.RolEnum;
import BuenSabor.model.Empleado;
import BuenSabor.model.Usuario;
import BuenSabor.repository.EmpleadoRepository;
import BuenSabor.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<EmpleadoDTO> listarEmpleadosConUsuariosExcepto(Long idEmpleadoExcluido) {
        List<Empleado> empleados = empleadoRepository.findAll()
                .stream()
                .filter(e -> !e.getId().equals(idEmpleadoExcluido))
                .toList();

        List<Usuario> usuarios = usuarioRepository.findByEmpleadoIn(empleados);

        Map<Long, Usuario> usuariosMap = usuarios.stream()
                .collect(Collectors.toMap(u -> u.getEmpleado().getId(), u -> u));

        return empleados.stream()
                .map(empleado -> {
                    Usuario usuario = usuariosMap.get(empleado.getId());

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

    @Transactional
    public Empleado crearEmpleado(EmpleadoRequestDTO dto) {
        validarRol(dto.getRol());

        Empleado empleado = new Empleado();
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setTelefono(dto.getTelefono());
        empleado.setEmail(dto.getEmail());
        empleado.setRol(dto.getRol());

        empleado = empleadoRepository.save(empleado);

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword() != null
                ? dto.getPassword()
                : dto.getUsername() + "123")); // password por defecto con usuario + "123"
        usuario.setEmpleado(empleado);
        usuario.setEstaActivo(true);

        usuarioRepository.save(usuario);

        return empleado;
    }

    @Transactional
    public Empleado editarEmpleado(EmpleadoRequestDTO dto) {
        validarRol(dto.getRol());

        Empleado empleado = empleadoRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setTelefono(dto.getTelefono());
        empleado.setEmail(dto.getEmail());
        empleado.setRol(dto.getRol());

        empleadoRepository.save(empleado);

        if (dto.getPassword() != null) {
            Usuario usuario = usuarioRepository.findByEmpleado(empleado)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
            usuarioRepository.save(usuario);
        }

        return empleado;
    }

    @Transactional
    public void darDeBajaEmpleado(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Usuario usuario = usuarioRepository.findByEmpleado(empleado)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.isEstaActivo()) {
            throw new RuntimeException("El empleado ya está dado de baja.");
        }

        usuario.setEstaActivo(false);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void activarEmpleado(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Usuario usuario = usuarioRepository.findByEmpleado(empleado)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.isEstaActivo()) {
            throw new RuntimeException("El empleado ya está activo.");
        }

        usuario.setEstaActivo(true);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarEmpleado(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        usuarioRepository.findByEmpleado(empleado).ifPresent(usuarioRepository::delete);

        empleadoRepository.delete(empleado);
    }

    private void validarRol(RolEnum rol) {
        if (rol == null || !Enum.valueOf(RolEnum.class, rol.name()).equals(rol)) {
            throw new IllegalArgumentException("Rol inválido. Debe ser uno de los valores definidos en RolEnum.");
        }
    }
}

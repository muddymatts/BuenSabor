package BuenSabor.mapper;

import BuenSabor.dto.usuario.UsuarioDTO;
import BuenSabor.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UsuarioDTOMapper {

    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO.EmpleadoDTO empleadoDTO = usuario.getEmpleado() != null
                ? new UsuarioDTO.EmpleadoDTO(
                usuario.getEmpleado().getNombre(),
                usuario.getEmpleado().getApellido(),
                usuario.getEmpleado().getEmail(),
                usuario.getEmpleado().getTelefono(),
                usuario.getEmpleado().getRol())
                : null;

        UsuarioDTO.ClienteDTO clienteDTO = usuario.getCliente() != null
                ? new UsuarioDTO.ClienteDTO(
                usuario.getCliente().getNombre(),
                usuario.getCliente().getApellido(),
                usuario.getCliente().getEmail(),
                usuario.getCliente().getTelefono())
                : null;

        return new UsuarioDTO(
                usuario.isEstaActivo(),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getNombre() : Objects.requireNonNull(usuario.getCliente()).getNombre(),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getApellido() : usuario.getCliente().getApellido(),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getEmail() : usuario.getCliente().getEmail(),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getTelefono() : usuario.getCliente().getTelefono(),
                usuario.getEmpleado() != null ? String.valueOf(usuario.getEmpleado().getRol()) : null,
                empleadoDTO,
                clienteDTO);
    }
}
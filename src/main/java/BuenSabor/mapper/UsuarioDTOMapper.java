package BuenSabor.mapper;

import BuenSabor.dto.usuario.UsuarioDTO;
import BuenSabor.enums.RolEnum;
import BuenSabor.model.Cliente;
import BuenSabor.model.Empleado;
import BuenSabor.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDTOMapper {

    public UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO.EmpleadoDTO empleadoDTO = null;

        if (usuario.getEmpleado() != null) {
            Empleado empleado = usuario.getEmpleado();
            empleadoDTO = new UsuarioDTO.EmpleadoDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getEmail(),
                    empleado.getTelefono(),
                    empleado.getRol()
            );
        }

        UsuarioDTO.ClienteDTO clienteDTO = null;

        if (usuario.getCliente() != null) {
            Cliente cliente = usuario.getCliente();
            clienteDTO = new UsuarioDTO.ClienteDTO(
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getEmail(),
                    cliente.getTelefono()
            );
        }

        String rol;

        if (usuario.getEmpleado() != null && usuario.getEmpleado().getRol() != null) {
            rol = usuario.getEmpleado().getRol().toString();
        } else {
            rol = RolEnum.CLIENTE.toString();
        }

        return new UsuarioDTO(
                usuario.isEstaActivo(),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getNombre() : (usuario.getCliente() != null ? usuario.getCliente().getNombre() : null),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getApellido() : (usuario.getCliente() != null ? usuario.getCliente().getApellido() : null),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getEmail() : (usuario.getCliente() != null ? usuario.getCliente().getEmail() : null),
                usuario.getEmpleado() != null ? usuario.getEmpleado().getTelefono() : (usuario.getCliente() != null ? usuario.getCliente().getTelefono() : null),
                rol,
                empleadoDTO,
                clienteDTO
        );
    }
}
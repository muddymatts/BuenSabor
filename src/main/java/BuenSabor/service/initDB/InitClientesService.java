package BuenSabor.service.initDB;

import BuenSabor.model.Cliente;
import BuenSabor.model.Domicilio;
import BuenSabor.model.Localidad;
import BuenSabor.model.Usuario;
import BuenSabor.repository.ClienteRepository;
import BuenSabor.repository.LocalidadRepository;
import BuenSabor.repository.UsuarioRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InitClientesService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalidadRepository localidadRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(InitClientesService.class.getName());

    public InitClientesService(ClienteRepository clienteRepository,
                               UsuarioRepository usuarioRepository,
                               LocalidadRepository localidadRepository,
                               PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.localidadRepository = localidadRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void setupClientes() {
        try {
            if (clienteRepository.count() == 0) {
                InputStream inputStream = getClass().getResourceAsStream("/data/usuarios/clientes.json");
                if (inputStream == null) {
                    throw new RuntimeException("Archivo 'clientes.json' no encontrado");
                }
                ObjectMapper objectMapper = new ObjectMapper();
                List<ClienteDTO> clientes = objectMapper.readValue(inputStream, new TypeReference<>() {
                });

                for (ClienteDTO clienteDTO : clientes) {
                    Cliente cliente = getCliente(clienteDTO);

                    clienteRepository.save(cliente);

                    Usuario usuario = new Usuario();
                    usuario.setUsername(clienteDTO.getUsername());
                    usuario.setPassword(passwordEncoder.encode(clienteDTO.getPassword()));
                    usuario.setEstaActivo(true);
                    usuario.setCliente(cliente);

                    usuarioRepository.save(usuario);
                }
                logger.info("Clientes y usuarios cargados exitosamente desde el archivo JSON.");
            } else {
                logger.info("La tabla de clientes no está vacía. No se cargaron nuevos datos.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar los datos de clientes y usuarios desde el archivo JSON.", e);
        }
    }

    private Cliente getCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEmail(clienteDTO.getEmail());

        Domicilio domicilio = new Domicilio();
        int localidadId = clienteDTO.getDireccion().getLocalidadId();
        Localidad localidad = localidadRepository.findById(localidadId)
                .orElseThrow(() -> new RuntimeException("Localidad no encontrada para ID: " + localidadId));
        domicilio.setLocalidad(localidad);

        domicilio.setCalle(clienteDTO.getDireccion().getCalle());
        domicilio.setNumero(domicilio.getNumero());
        domicilio.setCp(clienteDTO.getDireccion().getCodigoPostal());
        cliente.setDomicilio(domicilio);
        return cliente;
    }

    @Getter
    @Setter
    private static class ClienteDTO {
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
        private String username;
        private String password;
        private DireccionDTO direccion;

        @Getter
        @Setter
        private static class DireccionDTO {
            private int localidadId;
            private String calle;
            private int numeroCalle;
            private int codigoPostal;
        }
    }
}
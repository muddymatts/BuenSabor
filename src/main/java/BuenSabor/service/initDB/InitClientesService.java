package BuenSabor.service.initDB;

import BuenSabor.dto.jsonMapperDtos.ClienteJsonDTO;
import BuenSabor.model.Cliente;
import BuenSabor.model.Domicilio;
import BuenSabor.model.Usuario;
import BuenSabor.repository.ClienteRepository;
import BuenSabor.repository.DomicilioRepository;
import BuenSabor.repository.UsuarioRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final DomicilioRepository domicilioRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(InitClientesService.class.getName());

    public InitClientesService(ClienteRepository clienteRepository,
                               UsuarioRepository usuarioRepository,
                               DomicilioRepository domicilioRepository,
                               PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.domicilioRepository = domicilioRepository;
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

                List<ClienteJsonDTO> clientes = objectMapper.readValue(inputStream, new TypeReference<>() {
                });

                for (ClienteJsonDTO clienteDTO : clientes) {
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

    private Cliente getCliente(ClienteJsonDTO clienteDTO) {
        Cliente cliente = new Cliente();

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEmail(clienteDTO.getEmail());

        Domicilio domicilio = domicilioRepository.findById(clienteDTO.getDomicilioId())
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado para ID: " + clienteDTO.getDomicilioId()));

        cliente.setDomicilio(domicilio);

        return cliente;
    }
}
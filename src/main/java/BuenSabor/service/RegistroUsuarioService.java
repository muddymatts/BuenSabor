package BuenSabor.service;

import BuenSabor.dto.request.RegistroUsuarioRequest;
import BuenSabor.dto.response.RegistroResponse;
import BuenSabor.model.Cliente;
import BuenSabor.model.Domicilio;
import BuenSabor.model.Localidad;
import BuenSabor.model.Usuario;
import BuenSabor.repository.ClienteRepository;
import BuenSabor.repository.LocalidadRepository;
import BuenSabor.repository.ProvinciaRepository;
import BuenSabor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final LocalidadRepository localidadRepository;
    private final ProvinciaRepository provinciaRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistroUsuarioService(
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository,
            LocalidadRepository localidadRepository,
            ProvinciaRepository provinciaRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.localidadRepository = localidadRepository;
        this.provinciaRepository = provinciaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegistroResponse registrarUsuario(RegistroUsuarioRequest registroRequest) {

        // Verificar si el correo está en uso
        if (clienteRepository.existsByEmail(registroRequest.getEmail())) {
            throw new IllegalArgumentException("El correo ya está en uso.");
        }

        // Verificar si el username está en uso
        if (usuarioRepository.findByUsername(registroRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
        }

        // Recuperar la localidad por ID
        Localidad localidad = localidadRepository.findById(registroRequest.getLocalidadId())
                .orElseThrow(() -> new IllegalArgumentException("La localidad especificada no existe."));

        // Verificar que la localidad esté asociada a la provincia correcta
        if (localidad.getProvincia() == null || localidad.getProvincia().getId() != registroRequest.getProvinciaId()) {
            throw new IllegalArgumentException("La localidad no corresponde a la provincia especificada.");
        }

        // Verificar que la provincia esté asociada al país correcto
        if (localidad.getProvincia().getPais() == null || localidad.getProvincia().getPais().getId() != registroRequest.getPaisId()) {
            throw new IllegalArgumentException("La provincia no corresponde al país especificado.");
        }

        // Crear entidad Domicilio
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle(registroRequest.getDireccion().getCalle());
        domicilio.setNumero(registroRequest.getDireccion().getNumeroCalle());
        domicilio.setCp(registroRequest.getDireccion().getCodigoPostal());
        domicilio.setLocalidad(localidad);

        // Configurar y guardar Cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(registroRequest.getNombre());
        cliente.setApellido(registroRequest.getApellido());
        cliente.setEmail(registroRequest.getEmail());
        cliente.setTelefono(registroRequest.getTelefono());
        cliente.setDomicilio(domicilio);

        cliente = clienteRepository.save(cliente);

        // Configurar y guardar Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(registroRequest.getUsername());
        usuario.setPassword(passwordEncoder.encode(registroRequest.getPassword())); // Encriptar contraseña
        usuario.setCliente(cliente);

        usuarioRepository.save(usuario);

        return new RegistroResponse("Usuario registrado con éxito.", usuario.getId());
    }
}
package BuenSabor.service.auth;

import BuenSabor.dto.auth.AuthRequest;
import BuenSabor.dto.usuario.UsuarioDTO;
import BuenSabor.mapper.UsuarioDTOMapper;
import BuenSabor.model.Usuario;
import BuenSabor.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final UsuarioDTOMapper usuarioMapper;

    public AuthService(AuthenticationManager authManager,
                       CustomUserDetailsService userDetailsService,
                       JwtService jwtService,
                       UsuarioService usuarioService,
                       UsuarioDTOMapper usuarioMapper) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    public ResponseEntity<?> authenticateUser(AuthRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
            Map<String, Object> tokenData = jwtService.generateTokenWithExpiration(userDetails);

            Optional<Usuario> optionalUsuario = usuarioService.findByUsername(request.username());

            if (optionalUsuario.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            Usuario usuario = optionalUsuario.get();
            UsuarioDTO usuarioDTO = usuarioMapper.toUsuarioDTO(usuario);

            Map<String, Object> response = new HashMap<>();
            Map<String, Object> jwtObject = new HashMap<>();
            jwtObject.put("token", tokenData.get("token"));
            jwtObject.put("expirationDate", tokenData.get("expiration"));
            response.put("jwt", jwtObject);
            response.put("user", usuarioDTO);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ha ocurrido un error inesperado.");
        }
    }

    public ResponseEntity<UsuarioDTO> getUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Usuario> optionalUsuario = usuarioService.findByUsername(username);

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        Usuario usuario = optionalUsuario.get();
        UsuarioDTO usuarioDTO = usuarioMapper.toUsuarioDTO(usuario);

        return ResponseEntity.ok(usuarioDTO);
    }

}


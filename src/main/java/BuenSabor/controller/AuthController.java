package BuenSabor.controller;

import BuenSabor.dto.auth.AuthRequest;
import BuenSabor.dto.usuario.UsuarioDTO;
import BuenSabor.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        return authService.authenticateUser(request);
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> profile() {
        return authService.getUserProfile();
    }
}
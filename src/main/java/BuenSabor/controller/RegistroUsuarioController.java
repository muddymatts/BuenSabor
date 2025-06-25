package BuenSabor.controller;

import BuenSabor.dto.request.RegistroUsuarioRequest;
import BuenSabor.dto.response.RegistroResponse;
import BuenSabor.service.RegistroUsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registro-usuario")
public class RegistroUsuarioController {

    private final RegistroUsuarioService registroUsuarioService;

    public RegistroUsuarioController(RegistroUsuarioService registroUsuarioService) {
        this.registroUsuarioService = registroUsuarioService;
    }

    @PostMapping()
    public ResponseEntity<RegistroResponse> registrarUsuario(@RequestBody RegistroUsuarioRequest registroRequest) {
        RegistroResponse response = registroUsuarioService.registrarUsuario(registroRequest);
        return ResponseEntity.ok(response);
    }
}
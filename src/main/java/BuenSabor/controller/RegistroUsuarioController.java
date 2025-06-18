package BuenSabor.controller;

import BuenSabor.dto.request.RegistroUsuarioRequest;
import BuenSabor.dto.response.RegistroResponse;
import BuenSabor.service.RegistroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registro-usuario")
public class RegistroUsuarioController {

    private final RegistroUsuarioService registroUsuarioService;

    @Autowired
    public RegistroUsuarioController(RegistroUsuarioService registroUsuarioService) {
        this.registroUsuarioService = registroUsuarioService;
    }

    @PostMapping()
    public ResponseEntity<RegistroResponse> registrarUsuario(@RequestBody RegistroUsuarioRequest registroRequest) {
        RegistroResponse response = registroUsuarioService.registrarUsuario(registroRequest);
        return ResponseEntity.ok(response);
    }
}
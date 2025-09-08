package BuenSabor.controller;

import BuenSabor.dto.EmpleadoDTO;
import BuenSabor.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleados() {
        List<EmpleadoDTO> empleados = empleadoService.listarEmpleadosConUsuarios();
        return ResponseEntity.ok(empleados);
    }
}
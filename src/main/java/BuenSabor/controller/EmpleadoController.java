package BuenSabor.controller;

import BuenSabor.dto.EmpleadoDTO;
import BuenSabor.dto.EmpleadoRequestDTO;
import BuenSabor.model.Empleado;
import BuenSabor.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @GetMapping("/{id}")
    public ResponseEntity<List<EmpleadoDTO>> listarEmpleados(@PathVariable Long id) {
        List<EmpleadoDTO> empleados = empleadoService.listarEmpleadosConUsuariosExcepto(id);
        return ResponseEntity.ok(empleados);
    }

    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody EmpleadoRequestDTO empleadoRequest) {
        Empleado nuevoEmpleado = empleadoService.crearEmpleado(empleadoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> editarEmpleado(
            @PathVariable Long id,
            @RequestBody EmpleadoRequestDTO empleadoRequest) {
        empleadoRequest.setId(id);
        Empleado empleadoEditado = empleadoService.editarEmpleado(empleadoRequest);
        return ResponseEntity.ok(empleadoEditado);
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<Map<String, Object>> darDeBajaEmpleado(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            empleadoService.darDeBajaEmpleado(id);
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("message", "Empleado dado de baja exitosamente.");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            respuesta.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Map<String, Object>> activarEmpleado(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            empleadoService.activarEmpleado(id);
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("message", "Empleado activado exitosamente.");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            respuesta.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarEmpleado(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            empleadoService.eliminarEmpleado(id);
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("message", "Empleado eliminado exitosamente.");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            respuesta.put("status", HttpStatus.NOT_FOUND.value());
            respuesta.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
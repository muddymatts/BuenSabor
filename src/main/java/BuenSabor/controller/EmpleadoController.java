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
    public ResponseEntity<Map<String, Object>> crearEmpleado(@RequestBody EmpleadoRequestDTO empleadoRequest) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Empleado nuevoEmpleado = empleadoService.crearEmpleado(empleadoRequest);
            respuesta.put("status", HttpStatus.CREATED.value());
            respuesta.put("message", "Empleado creado exitosamente.");
            respuesta.put("empleado", nuevoEmpleado); // Incluye información del nuevo empleado si es necesario
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } catch (RuntimeException e) {
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            respuesta.put("message", "Error al crear el empleado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editarEmpleado(
            @PathVariable Long id,
            @RequestBody EmpleadoRequestDTO empleadoRequest) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            empleadoRequest.setId(id);
            empleadoService.editarEmpleado(empleadoRequest);
            respuesta.put("status", HttpStatus.OK.value());
            respuesta.put("message", "Empleado editado exitosamente.");
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException e) {
            respuesta.put("status", HttpStatus.BAD_REQUEST.value());
            respuesta.put("message", "Error al editar el empleado: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
        }
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
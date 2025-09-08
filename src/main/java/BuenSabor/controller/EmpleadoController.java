package BuenSabor.controller;

import BuenSabor.dto.EmpleadoDTO;
import BuenSabor.dto.EmpleadoRequestDTO;
import BuenSabor.model.Empleado;
import BuenSabor.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> darDeBajaEmpleado(@PathVariable Long id) {
        empleadoService.darDeBajaEmpleado(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }
}
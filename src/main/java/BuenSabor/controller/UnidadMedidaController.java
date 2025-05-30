package BuenSabor.controller;

import BuenSabor.model.UnidadMedida;
import BuenSabor.service.UnidadMedidaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades-medida")
public class UnidadMedidaController {
    private final UnidadMedidaService service;

    public UnidadMedidaController(UnidadMedidaService service) {
        this.service = service;
    }

    @PostMapping
    public UnidadMedida crear(@RequestBody UnidadMedida unidad) {
        return service.crear(unidad);
    }

    @PutMapping("/{id}")
    public UnidadMedida editar(@PathVariable Long id, @RequestBody UnidadMedida nuevaData) {
        UnidadMedida unidad = service.buscarPorId(id);
        unidad.setDenominacion(nuevaData.getDenominacion());
        return service.actualizar(unidad);
    }

    @GetMapping
    public List<UnidadMedida> listarTodas() {
        return service.listarTodas();
    }
}

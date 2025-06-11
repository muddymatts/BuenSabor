package BuenSabor.controller;

import BuenSabor.model.Localidad;
import BuenSabor.service.LocalidadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/localidades")
public class LocalidadController {

    private final LocalidadService localidadService;

    public LocalidadController(LocalidadService localidadService) {
        this.localidadService = localidadService;
    }

    @GetMapping
    public List<Localidad> listarLocalidades() {
        return localidadService.findAll();
    }

    @GetMapping("/{provinciaId}")
    public List<Localidad> listarLocalidadesPorProvincia(@PathVariable("provinciaId") int provinciaId) {
        return localidadService.findByProvinciaId(provinciaId);
    }
}

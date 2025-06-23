package BuenSabor.controller;

import BuenSabor.dto.localidad.LocalidadDTO;
import BuenSabor.model.Localidad;
import BuenSabor.service.LocalidadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/localidades")
public class LocalidadController {

    private final LocalidadService localidadService;

    public LocalidadController(LocalidadService localidadService) {
        this.localidadService = localidadService;
    }

    @GetMapping
    public List<LocalidadDTO> listarLocalidades() {
        List<Localidad> localidades = localidadService.findAll();
        return localidadService.mapToDTOList(localidades);
    }

    @GetMapping("/{provinciaId}")
    public List<LocalidadDTO> listarLocalidadesPorProvincia(@PathVariable("provinciaId") int provinciaId) {
        List<Localidad> localidades = localidadService.findByProvinciaId(provinciaId);
        return localidadService.mapToDTOList(localidades);
    }
}

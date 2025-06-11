package BuenSabor.controller;

import BuenSabor.model.Provincia;
import BuenSabor.service.ProvinciaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {

    private final ProvinciaService provinciaService;

    public ProvinciaController(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    @GetMapping
    public List<Provincia> listarProvincias() {
        return provinciaService.findAll();
    }

    @GetMapping("/{id}")
    public List<Provincia> listarProvinciasPorPais(@PathVariable Integer id) {
        return provinciaService.findProvinciasByPaisId(id);
    }
}

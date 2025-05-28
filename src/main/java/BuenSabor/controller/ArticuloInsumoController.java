package BuenSabor.controller;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.service.ArticuloInsumoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-insumo")
public class ArticuloInsumoController {

    private final ArticuloInsumoService service;

    public ArticuloInsumoController(ArticuloInsumoService service) {
        this.service = service;
    }

    @PostMapping
    public ArticuloInsumo crear(@RequestBody ArticuloInsumo insumo) {
        return service.crear(insumo);
    }

    @GetMapping
    public List<ArticuloInsumo> listarTodas() {
        return service.listarTodas();
    }
}
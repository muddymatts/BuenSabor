package BuenSabor.controller;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.service.ArticuloManufacturadoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articulos-manufacturados")
public class ArticuloManufacturadoController {

    private final ArticuloManufacturadoService service;

    public ArticuloManufacturadoController(ArticuloManufacturadoService service) {
        this.service = service;
    }

    @PostMapping
    public ArticuloManufacturado crear(@RequestBody ArticuloManufacturado articulo) {
        return service.crear(articulo);
    }

    @GetMapping("/{id}")
    public ArticuloManufacturado buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
package BuenSabor.controller;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.service.ArticuloManufacturadoService;
import BuenSabor.service.BajaLogicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-manufacturados")
public class ArticuloManufacturadoController {

    private final BajaLogicaService bajaLogicaService;

    private final ArticuloManufacturadoService service;

    public ArticuloManufacturadoController(ArticuloManufacturadoService service, BajaLogicaService bajaLogicaService) {
        this.service = service;
        this.bajaLogicaService = bajaLogicaService;
    }

    @PostMapping
    public ArticuloManufacturado crear(@RequestBody ArticuloManufacturado articulo) {
        return service.crear(articulo);
    }

    @GetMapping("/{id}")
    public ArticuloManufacturado buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/listar")
    public List<ArticuloManufacturado> listar() {
        return service.findByFechaBajaIsNull();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> bajaLogica(@PathVariable Long id) {
        bajaLogicaService.darDeBaja(ArticuloManufacturado.class, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<ArticuloManufacturado> mostrarTodos() {
        return service.findAll();
    }

}
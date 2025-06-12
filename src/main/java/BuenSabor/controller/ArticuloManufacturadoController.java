package BuenSabor.controller;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.service.ArticuloManufacturadoService;
import BuenSabor.service.BajaLogicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-manufacturados")
public class ArticuloManufacturadoController {

    private final BajaLogicaService bajaLogicaService;

    private final ArticuloManufacturadoService service;
    private final ArticuloManufacturadoService articuloManufacturadoService;

    public ArticuloManufacturadoController(ArticuloManufacturadoService service, BajaLogicaService bajaLogicaService, ArticuloManufacturadoService articuloManufacturadoService) {
        this.service = service;
        this.bajaLogicaService = bajaLogicaService;
        this.articuloManufacturadoService = articuloManufacturadoService;
    }

    @PostMapping
    public  ResponseEntity<ArticuloManufacturado> crear(@RequestBody ArticuloManufacturado articulo) {
        ArticuloManufacturado nuevo = service.crear(articulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloManufacturado> buscarPorId(@PathVariable Long id) {
        ArticuloManufacturado busqueda = service.buscarPorId(id);
        if (busqueda != null) {
            return ResponseEntity.ok(busqueda);
            } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar")
    public List<ArticuloManufacturado> listar() {
        return service.findByFechaBajaIsNull();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> bajaLogica(@PathVariable Long id) {
        String message = articuloManufacturadoService.eliminarArticuloManufacturado(id) + ", baja lógica realizada correctamente.";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/mostrarTodos")
    public List<ArticuloManufacturado> mostrarTodos() {
        return service.findAll();
    }

}
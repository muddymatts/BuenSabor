package BuenSabor.controller;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.service.ArticuloManufacturadoService;
import BuenSabor.service.BajaLogicaService;
import jakarta.persistence.EntityNotFoundException;
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

    @GetMapping
    public List<ArticuloManufacturado> mostrarTodos() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarArticuloManufacturado(@PathVariable Long id) {
        try {
            String nombreArticulo = service.eliminarArticuloManufacturado(id);
            String mensaje = "El artículo manufacturado \"" + nombreArticulo + "\" fue eliminado correctamente.";

            return ResponseEntity.ok(mensaje);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(404).body("Error: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Ocurrió un error al intentar eliminar el artículo manufacturado.");
        }
    }
}
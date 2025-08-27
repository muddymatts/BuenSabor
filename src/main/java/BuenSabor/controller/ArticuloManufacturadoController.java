package BuenSabor.controller;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDTO;
import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.service.ArticuloManufacturadoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-manufacturados")
public class ArticuloManufacturadoController {

    private final ArticuloManufacturadoService articuloManufacturadoService;

    public ArticuloManufacturadoController(ArticuloManufacturadoService articuloManufacturadoService) {
        this.articuloManufacturadoService = articuloManufacturadoService;
    }

    @PostMapping
    public  ResponseEntity<ArticuloManufacturado> create (@RequestBody ArticuloManufacturado articulo) {
        ArticuloManufacturado nuevo = articuloManufacturadoService.guardar(articulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloManufacturado> getArticuloManufacturado(@PathVariable Long id) {
        ArticuloManufacturado busqueda = articuloManufacturadoService.getArticuloManufacturado(id);
        if (busqueda != null) {
            return ResponseEntity.ok(busqueda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ArticuloManufacturadoDTO>> getArticulosManufacturados() {
        return ResponseEntity.ok().body(articuloManufacturadoService.getArticulosManufacturadoDTO());
    }

    /*
    @GetMapping("/listar")
    public List<ArticuloManufacturado> listar() {
        return articuloManufacturadoService.findByFechaBajaIsNull();
    }
  */

    @GetMapping("/listar")
    public List<ArticuloManufacturado> mostrarTodos() {
        return articuloManufacturadoService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarArticuloManufacturado(@PathVariable Long id) {
        try {
            String nombreArticulo = articuloManufacturadoService.eliminarArticuloManufacturado(id);
            String mensaje = "El artículo manufacturado \"" + nombreArticulo + "\" fue eliminado correctamente.";

            return ResponseEntity.ok(mensaje);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(404).body("Error: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Ocurrió un error al intentar eliminar el artículo manufacturado.");
        }
    }
}
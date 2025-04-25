package BuenSabor.controller;

import BuenSabor.model.CategoriaArticulo;
import BuenSabor.service.CategoriaArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-articulo")
@CrossOrigin(origins = "*")
public class CategoriaArticuloController {

    @Autowired
    private CategoriaArticuloService categoriaArticuloService;

    @PostMapping
    public CategoriaArticulo crear(@RequestBody CategoriaArticulo categoria) {
        return categoriaArticuloService.crear(categoria);
    }

    @GetMapping
    public List<CategoriaArticulo> obtenerTodas() {
        return categoriaArticuloService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public CategoriaArticulo obtenerPorId(@PathVariable Long id) {
        return categoriaArticuloService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaArticulo actualizar(@PathVariable Long id, @RequestBody CategoriaArticulo nuevaData) {
        return categoriaArticuloService.actualizar(id, nuevaData);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        categoriaArticuloService.eliminar(id);
    }
}
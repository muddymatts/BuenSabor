package BuenSabor.controller;

import BuenSabor.model.CategoriaArticuloInsumo;
import BuenSabor.service.CategoriaArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-articulo")
//@CrossOrigin(origins = "*")
public class CategoriaArticuloController {

    @Autowired
    private CategoriaArticuloService categoriaArticuloService;

    @PostMapping
    public CategoriaArticuloInsumo crear(@RequestBody CategoriaArticuloInsumo categoria) {
        return categoriaArticuloService.crear(categoria);
    }

    @GetMapping
    public List<CategoriaArticuloInsumo> obtenerTodas() {
        return categoriaArticuloService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public CategoriaArticuloInsumo obtenerPorId(@PathVariable Long id) {
        return categoriaArticuloService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaArticuloInsumo actualizar(@PathVariable Long id, @RequestBody CategoriaArticuloInsumo nuevaData) {
        return categoriaArticuloService.actualizar(id, nuevaData);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        categoriaArticuloService.eliminar(id);
    }
}
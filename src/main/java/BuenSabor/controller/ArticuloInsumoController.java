package BuenSabor.controller;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.service.articuloInsumo.ArticuloInsumoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/articulos-insumo")
public class ArticuloInsumoController {

    private final ArticuloInsumoService service;

    public ArticuloInsumoController(ArticuloInsumoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ArticuloInsumo> crear(@RequestBody ArticuloInsumo insumo) {
        ArticuloInsumo nuevoInsumo = service.crear(insumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoInsumo);
    }

    @GetMapping
    public List<ArticuloInsumo> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloInsumo> getArticuloInsumo(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArticuloInsumo> bajaInsumo(@PathVariable Long id) {
        return ResponseEntity.ok(service.bajaLogica(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArticuloInsumo> reestablecer(@PathVariable Long id){
        return ResponseEntity.ok(service.anularBaja(id));
    }

    @PutMapping
    public ResponseEntity<ArticuloInsumo> editarInsumo(@RequestBody ArticuloInsumo insumo){
        if (insumo.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No se puede editar un insumo sin ID");
        } else {
            return ResponseEntity.ok(service.editarInsumo(insumo));
        }
    }
}
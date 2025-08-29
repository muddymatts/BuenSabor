package BuenSabor.controller;


import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.model.Promocion;
import BuenSabor.service.promocion.PromocionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/promociones")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    @GetMapping
    public Iterable<PromocionDTO> listarPromociones() {
        return promocionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromocionDTO> getPromocion(@PathVariable Long id) {
        return ResponseEntity.ok(promocionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PromocionDTO> crearPromocion(@RequestBody Promocion promocion) {
        return ResponseEntity.ok(promocionService.guardarPromocion(promocion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocionDTO> editarPromocion(@PathVariable Long id, @RequestBody Promocion promocion) {
        if (!id.equals(promocion.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID de la URL no coincide con el del cuerpo.");
        }
        return ResponseEntity.ok(promocionService.editarPromocion(promocion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPromocion(@PathVariable Long id) {
        promocionService.eliminarPromocion(id);
        return ResponseEntity.noContent().build();
    }
}

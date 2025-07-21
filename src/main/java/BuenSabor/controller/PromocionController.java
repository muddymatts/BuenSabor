package BuenSabor.controller;


import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.model.Promocion;
import BuenSabor.service.promocion.PromocionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PromocionDTO> getPromocion (@PathVariable Long id) {
        return ResponseEntity.ok(promocionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PromocionDTO> crearPromocion(@RequestBody Promocion promocion) {
        return ResponseEntity.ok(promocionService.guardar(promocion));
    }
}

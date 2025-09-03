package BuenSabor.controller;

import BuenSabor.dto.response.ResponseDTO;
import BuenSabor.enums.Estado;
import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.PedidoVenta;
import BuenSabor.service.BajaLogicaService;
import BuenSabor.service.PedidoVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoVentaController {

    private final PedidoVentaService pedidoVentaService;

    public PedidoVentaController(PedidoVentaService pedidoVentaService) {
        this.pedidoVentaService = pedidoVentaService;
    }

    @PostMapping
    public ResponseEntity<PedidoVenta> crear(@RequestBody PedidoVenta pedido) {
        PedidoVenta nuevoPedido = pedidoVentaService.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoVenta>> listar() {
        List<PedidoVenta> pedidos = pedidoVentaService.listarTodas();
        return ResponseEntity.ok(pedidos);
    }

    @DeleteMapping("/{id}")
    public String bajaLogica(@PathVariable Long id) {
        String idPedido = pedidoVentaService.darDeBaja(id);
        String mensaje = "El pedido con id " + idPedido + " ha sido eliminado correctamente.";
        return mensaje;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoVenta> buscarPorId(@PathVariable Long id) {
        PedidoVenta busqueda = pedidoVentaService.buscarPorId(id);

        if (busqueda != null) {
            return ResponseEntity.ok(busqueda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        } else if (Stream.of(Estado.values())
                .anyMatch(e -> e.name().equals(estado.toLowerCase().trim()))){
            PedidoVenta pedido = pedidoVentaService.actualizarEstado(id, Estado.valueOf(estado.toLowerCase().trim()));
            return ResponseEntity.ok(new ResponseDTO("Pedido actualizado.", pedido.getId()));
        } else {
            throw new IllegalArgumentException("El estado debe ser uno de los siguientes: " + Arrays.toString(Estado.values()) + ".");
        }
    }

}

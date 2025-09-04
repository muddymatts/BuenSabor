package BuenSabor.controller;

import BuenSabor.dto.pedidoVenta.PedidoVentaResumenDTO;
import BuenSabor.dto.response.ResponseDTO;
import BuenSabor.enums.Estado;
import BuenSabor.mapper.PedidoVentaMapper;
import BuenSabor.model.PedidoVenta;
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
    private final PedidoVentaMapper pedidoVentaMapper;

    public PedidoVentaController(PedidoVentaService pedidoVentaService, PedidoVentaMapper pedidoVentaMapper) {
        this.pedidoVentaService = pedidoVentaService;
        this.pedidoVentaMapper = pedidoVentaMapper;
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
        return "El pedido con id " + idPedido + " ha sido eliminado correctamente.";
    }

    // trae todos los datos para admin
    @GetMapping("/{id}")
    public ResponseEntity<PedidoVenta> buscarPorId(@PathVariable Long id) {
        PedidoVenta busqueda = pedidoVentaService.buscarPorId(id);

        if (busqueda != null) {
            return ResponseEntity.ok(busqueda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // para seguimiento del pedido
    @GetMapping("/{id}/resumen")
    public ResponseEntity<PedidoVentaResumenDTO> buscarResumenPorId(@PathVariable Long id) {
        PedidoVenta pedido = pedidoVentaService.buscarPorId(id);
        return ResponseEntity.ok(pedidoVentaMapper.toResumenDTO(pedido));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        } else if (Stream.of(Estado.values())
                .anyMatch(e -> e.name().equals(estado.toLowerCase().trim()))) {
            PedidoVenta pedido = pedidoVentaService.actualizarEstado(id, Estado.valueOf(estado.toLowerCase().trim()));
            return ResponseEntity.ok(new ResponseDTO("Pedido actualizado.", pedido.getId()));
        } else {
            throw new IllegalArgumentException("El estado debe ser uno de los siguientes: " + Arrays.toString(Estado.values()) + ".");
        }
    }

}

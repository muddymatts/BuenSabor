package BuenSabor.controller;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.PedidoVenta;
import BuenSabor.service.BajaLogicaService;
import BuenSabor.service.PedidoVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println(mensaje);
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

}

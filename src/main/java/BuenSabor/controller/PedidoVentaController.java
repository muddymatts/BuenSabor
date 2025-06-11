package BuenSabor.controller;

import BuenSabor.model.PedidoVenta;
import BuenSabor.service.BajaLogicaService;
import BuenSabor.service.PedidoVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoVentaController {

    private final BajaLogicaService bajaLogicaService;
    private final PedidoVentaService pedidoVentaService;

    public PedidoVentaController(BajaLogicaService bajaLogicaService, PedidoVentaService pedidoVentaService) {
        this.bajaLogicaService = bajaLogicaService;
        this.pedidoVentaService = pedidoVentaService;
    }

    @PostMapping
    public ResponseEntity<PedidoVenta> crear(@RequestBody PedidoVenta pedido) {
        PedidoVenta nuevoPedido = pedidoVentaService.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

}

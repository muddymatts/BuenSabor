package BuenSabor.controller;

import BuenSabor.dto.response.ResponseDTO;
import BuenSabor.enums.FormaPago;
import BuenSabor.model.FacturaVenta;
import BuenSabor.service.facturaVenta.FacturaVentaSevice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facturas")
public class FacturaVentaController {

    private final FacturaVentaSevice facturaVentaService;

    public FacturaVentaController(FacturaVentaSevice facturaVentaService) {
        this.facturaVentaService = facturaVentaService;
    }

    @PutMapping("/facturar/{idPedido}")
    public ResponseEntity<ResponseDTO> facturarPedido(@PathVariable Long idPedido) {
        FacturaVenta factura = facturaVentaService.facturarPedido(idPedido, FormaPago.Efectivo);
        return ResponseEntity.ok(new ResponseDTO("Pedido Facturado Correctamente", factura.getId()));
    }
}

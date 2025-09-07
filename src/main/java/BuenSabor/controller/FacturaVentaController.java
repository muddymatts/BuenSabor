package BuenSabor.controller;

import BuenSabor.dto.response.ResponseDTO;
import BuenSabor.enums.FormaPago;
import BuenSabor.model.FacturaVenta;
import BuenSabor.service.facturaVenta.FacturaPdfService;
import BuenSabor.service.facturaVenta.FacturaVentaSevice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
public class FacturaVentaController {

    private final FacturaVentaSevice facturaVentaService;
    private final FacturaPdfService facturaPdfService;

    public FacturaVentaController(FacturaVentaSevice facturaVentaService,
                                  FacturaPdfService facturaPdfService) {
        this.facturaVentaService = facturaVentaService;
        this.facturaPdfService = facturaPdfService;
    }

    @PutMapping("/facturar/{idPedido}")
    public ResponseEntity<ResponseDTO> facturarPedidoEfectivo(@PathVariable Long idPedido) {
        FacturaVenta factura = facturaVentaService.facturarPedido(idPedido, FormaPago.Efectivo);
        return ResponseEntity.ok(new ResponseDTO("Pedido Facturado Correctamente", factura.getId()));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generarFacturaPdf(@PathVariable Long id) throws Exception {
        FacturaVenta factura = facturaVentaService.getFactura(id);

        byte[] pdfBytes = facturaPdfService.generarPdf(factura);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=factura-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}

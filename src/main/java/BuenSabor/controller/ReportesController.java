package BuenSabor.controller;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDTO;
import BuenSabor.dto.reportes.ReporteVentasDTO;
import BuenSabor.service.PedidoVentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/reportes")
public class ReportesController {

    private final PedidoVentaService pedidoVentaService;

    public ReportesController(PedidoVentaService pedidoVentaService) {
        this.pedidoVentaService = pedidoVentaService;
    }

    @GetMapping("ventas")
    public ResponseEntity<ReporteVentasDTO> getVentas(){
        return ResponseEntity.ok(pedidoVentaService.getVentasDTO());
    }

    @GetMapping("topVentas")
    public ResponseEntity<List<ArticuloManufacturadoDTO>> getTopVentas(){
        return null;
    }

}

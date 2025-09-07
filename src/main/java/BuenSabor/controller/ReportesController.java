package BuenSabor.controller;

import BuenSabor.dto.reportes.ReporteDTO;
import BuenSabor.service.ReportesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReportesController {

    private final ReportesService reportesService;

    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    @GetMapping("{idSucursal}")
    public ResponseEntity<ReporteDTO> getPedidosPorFecha(@PathVariable Long idSucursal,
                                                         @RequestParam(required = true) LocalDate fechaDesde,
                                                         @RequestParam(required = true) LocalDate fechaHasta,
                                                         @RequestParam(required = true) String tipoReporte){
        switch (tipoReporte){
            case("ventas") : {
                ReporteDTO respuesta = reportesService.getVentasPorFechas(idSucursal,fechaDesde,fechaHasta);
                return ResponseEntity.ok(respuesta);
            } case("compras") : {

            } case("stock") : {}
            default: return ResponseEntity.badRequest().build();
        }
    }
}

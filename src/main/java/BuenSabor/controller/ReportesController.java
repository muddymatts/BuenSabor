package BuenSabor.controller;

import BuenSabor.dto.reportes.ReporteDTO;
import BuenSabor.service.ReportesService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
            } case("productos") : {
                ReporteDTO respuesta = reportesService.getProductoPorFechas(idSucursal,fechaDesde,fechaHasta);
                return ResponseEntity.ok(respuesta);
            } case("topDia") : {}
            default: return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{idSucursal}/excel")
    public ResponseEntity<byte[]> descargarExcel(
            @PathVariable Long idSucursal,
            @RequestParam String fechaDesde,
            @RequestParam String fechaHasta,
            @RequestParam String tipoReporte
    ) throws IOException {

        // ⚡ Acá pedimos al servicio los mismos datos que usás para el chart
        List<Map<String, String>> datos = reportesService.obtenerDatosReporte(
                idSucursal, fechaDesde, fechaHasta, tipoReporte
        );

        // Generamos el Excel
        byte[] excel = reportesService.generarExcel(datos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=reporte-" + tipoReporte + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excel);
    }
}

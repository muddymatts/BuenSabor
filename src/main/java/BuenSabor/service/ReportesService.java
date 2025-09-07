package BuenSabor.service;

import BuenSabor.dto.reportes.DetalleReporteDTO;
import BuenSabor.dto.reportes.ReporteDTO;
import BuenSabor.model.FacturaVenta;
import BuenSabor.repository.facturaVenta.FacturaVentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportesService {
    private final FacturaVentaRepository facturaVentaRepository;

    public ReportesService(FacturaVentaRepository facturaVentaRepository) {
        this.facturaVentaRepository = facturaVentaRepository;
    }

    public ReporteDTO getVentasPorFechas(Long idSucursal, LocalDate fechaDesde, LocalDate fechaHasta) {
        ReporteDTO reporte = new ReporteDTO();
        List<FacturaVenta> facturas = facturaVentaRepository.findAllByFechaFacturacionBetween(fechaDesde,fechaHasta);

        Map<LocalDate, BigDecimal> montoPorFecha = facturas.stream()
                .collect(Collectors.groupingBy(
                        FacturaVenta::getFechaFacturacion,
                        Collectors.reducing(BigDecimal.ZERO, FacturaVenta::getTotalVenta, BigDecimal::add)
                ));

        List<DetalleReporteDTO> detalles = montoPorFecha.entrySet().stream()
                .map(entry -> {
                    DetalleReporteDTO detalle = new DetalleReporteDTO();
                    detalle.setEjeX(entry.getKey().toString());              // fecha como eje X
                    detalle.setEjeY(entry.getValue().toPlainString());       // monto como eje Y
                    return detalle;
                })
                .sorted(Comparator.comparing(DetalleReporteDTO::getEjeX))
                .toList();

        reporte.setNombreSucursal("Sucursal " + idSucursal); // o buscás el nombre real
        reporte.setTipoReporte("Ventas por fecha");
        reporte.setDescripcion("Monto total de ventas por fecha");
        reporte.setFechaDesde(fechaDesde.toString());
        reporte.setFechaHasta(fechaHasta.toString());
        reporte.setDetalles(detalles);

        return reporte;
    }
}

package BuenSabor.service;

import BuenSabor.dto.reportes.DetalleReporteDTO;
import BuenSabor.dto.reportes.ReporteDTO;
import BuenSabor.model.FacturaVenta;
import BuenSabor.model.FacturaVentaDetalle;
import BuenSabor.repository.facturaVenta.FacturaVentaRepository;
import org.springframework.stereotype.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    public ReporteDTO getProductoPorFechas(Long idSucursal, LocalDate fechaDesde, LocalDate fechaHasta) {
        ReporteDTO reporte = new ReporteDTO();
        List<FacturaVenta> facturas = facturaVentaRepository.findAllByFechaFacturacionBetween(fechaDesde,fechaHasta);

        Map<String, Long> productosVendidos = facturas.stream()
                .flatMap(f -> f.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        FacturaVentaDetalle::getNombreProducto,
                        Collectors.summingLong(FacturaVentaDetalle::getCantidad)
                ));

        List<DetalleReporteDTO> detalles = productosVendidos.entrySet().stream()
                .map(entry -> {
                    DetalleReporteDTO detalle = new DetalleReporteDTO();
                    detalle.setEjeX(entry.getKey());
                    detalle.setEjeY(entry.getValue().toString());
                    return detalle;
                })
                .sorted(Comparator.comparing(DetalleReporteDTO::getEjeX))
                .toList();

        reporte.setNombreSucursal("Sucursal " + idSucursal);
        reporte.setTipoReporte("Productos vendidos por fechas");
        reporte.setDescripcion("Cantidad de productos vendidos entre fechas");
        reporte.setFechaDesde(fechaDesde.toString());
        reporte.setFechaHasta(fechaHasta.toString());
        reporte.setDetalles(detalles);
        return reporte;
    }

    public byte[] generarExcel(List<Map<String, String>> datos) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte");

            if (datos.isEmpty()) {
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("No hay datos para los filtros seleccionados");
            } else {
                // Cabecera (a partir de las claves del primer map)
                Row headerRow = sheet.createRow(0);
                int col = 0;
                for (String key : datos.get(0).keySet()) {
                    headerRow.createCell(col++).setCellValue(key);
                }

                // Filas con datos
                int rowNum = 1;
                for (Map<String, String> fila : datos) {
                    Row row = sheet.createRow(rowNum++);
                    col = 0;
                    for (Object valor : fila.values()) {
                        Cell cell = row.createCell(col++);
                        if (valor instanceof Number) {
                            cell.setCellValue(((Number) valor).doubleValue());
                        } else {
                            cell.setCellValue(valor.toString());
                        }
                    }
                }

                // Ajustar ancho automático
                for (int i = 0; i < datos.get(0).size(); i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }

    public List<Map<String, String>> obtenerDatosReporte(Long idSucursal, String fechaDesdeStr, String fechaHastaStr, String tipoReporte) {
        // ⚡ Esto deberías reemplazarlo con tu query real
        LocalDate fechaDesde = LocalDate.parse(fechaDesdeStr);
        LocalDate fechaHasta = LocalDate.parse(fechaHastaStr);

        if (tipoReporte.equalsIgnoreCase("ventas")) {
            ReporteDTO reporte = getVentasPorFechas(idSucursal, fechaDesde, fechaHasta);

            // mapear detalles a List<Map<String, Object>>
            return reporte.getDetalles().stream()
                    .map(d -> Map.of(
                            "Fecha", d.getEjeX(),
                            "Ventas", d.getEjeY()
                    ))
                    .toList();
        } else if (tipoReporte.equalsIgnoreCase("productos")) {
            ReporteDTO reporte = getProductoPorFechas(idSucursal, fechaDesde, fechaHasta);

            // mapear detalles a List<Map<String, Object>>
            return reporte.getDetalles().stream()
                    .map(d -> Map.of(
                            "Producto", d.getEjeX(),
                            "Cantidad", d.getEjeY()
                    ))
                    .toList();
        }

        return List.of(); // si no hay datos
    }

}

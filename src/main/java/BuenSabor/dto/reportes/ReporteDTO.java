package BuenSabor.dto.reportes;

import lombok.Data;

import java.util.List;

@Data
public class ReporteDTO {
    private String nombreSucursal;
    private String tipoReporte;
    private String descripcion;
    private String fechaDesde;
    private String fechaHasta;
    private List<DetalleReporteDTO> detalles;

}

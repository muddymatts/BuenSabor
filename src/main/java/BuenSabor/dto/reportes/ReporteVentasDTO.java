package BuenSabor.dto.reportes;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReporteVentasDTO {
    private String fechaInicio;
    private String fechaFin;
    private Integer cantidadVentas;
    private Double totalVentas;
    private Double totalCostos;
    private List<DetalleVentasDTO> detalles = new ArrayList<>();
}

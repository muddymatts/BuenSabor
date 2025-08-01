package BuenSabor.dto.reportes;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DetalleVentasDTO {
    private Integer idSucursal;
    private String nombreSucursal;
    private List<VentasSucursalDTO> ventas = new ArrayList<>();
}

package BuenSabor.dto.promocion;

import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PromocionDTO {

    private Long id;
    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private double descuento;
    private List<PromocionDetalleDTO> detalle;
    private double precioSinDescuento;
    private double precioVenta;
    private List<String> imagenes = new ArrayList<>();
}

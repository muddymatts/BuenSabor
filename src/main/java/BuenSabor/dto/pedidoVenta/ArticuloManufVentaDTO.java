package BuenSabor.dto.pedidoVenta;

import java.util.List;

public record ArticuloManufVentaDTO(
        Long id,
        String denominacion,
        String descripcion,
        double precioVenta,
        String categoria,
        List<String> listaImagenes
) {
}

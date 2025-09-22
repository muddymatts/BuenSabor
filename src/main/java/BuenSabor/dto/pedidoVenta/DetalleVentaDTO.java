package BuenSabor.dto.pedidoVenta;

public record DetalleVentaDTO(
        Long id,
        Integer cantidad,
        ArticuloManufVentaDTO articuloManufacturado,
        Object promocion
) {
}


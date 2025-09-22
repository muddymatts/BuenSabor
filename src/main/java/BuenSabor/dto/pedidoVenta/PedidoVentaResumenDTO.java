package BuenSabor.dto.pedidoVenta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoVentaResumenDTO(
        Long id,
        LocalDate fechaAlta,
        LocalDateTime horaEstimadaFinalizacion,
        Double subtotal,
        Double gastosEnvio,
        Double total,
        String estado,
        String tipoEnvio,
        LocalDateTime fechaHoraPedido,
        Object facturaVenta,
        SucursalEmpresaDTO sucursalEmpresa,
        ClienteVentaDTO cliente,
        List<DetalleVentaDTO> detalles
) {
}

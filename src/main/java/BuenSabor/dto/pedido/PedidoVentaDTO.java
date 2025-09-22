package BuenSabor.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PedidoVentaDTO {
    private Long idPedido;
    private Long idSucursal;
    private String nombreSucursal;
    private Long idCliente;
    private String estadoPedido;
    private String tipoEnvio;
    private Long idDireccionEntrega;
    private String direccionEntrega;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaBaja;
    private LocalDateTime horaEstimadaFinalizacion;
    private BigDecimal total;
    private BigDecimal subtotal;
    private BigDecimal gastosEnvio;
    private List<PedidoVentaDetalleDTO> detalles;
    private Long idFactura;
}

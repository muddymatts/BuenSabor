package BuenSabor.dto.pedido;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoVentaDetalleDTO {
    private int cantidad;
    private String tipoItem;
    private Long itemId;
    private BigDecimal precioVenta;
    private BigDecimal subTotal;
    private String denominacion;
    private List<String> categorias = new ArrayList<>();
}

package BuenSabor.dto.mercadoPago.preferenceMp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencePedidoDTO {
    private BigDecimal montoCarrito;
    private List<PreferenceItemDTO> items;
    private String idPedido;
}

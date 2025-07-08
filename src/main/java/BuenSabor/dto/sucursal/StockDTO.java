package BuenSabor.dto.sucursal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockDTO {
    private Long idInsumo;
    private String denominacion;
    private int cantidadMinima;
    private int cantidadMaxima;
    private int cantidadActual;
    private String UnidadMedida;
}

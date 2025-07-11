package BuenSabor.dto.sucursal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SucursalInsumoDTO {
    private Long idInsumo;
    private String denominacion;
    private Long cantidadMinima;
    private Long cantidadMaxima;
    private Long cantidadActual;
    private String UnidadMedida;
    private List<String> categorias;
}

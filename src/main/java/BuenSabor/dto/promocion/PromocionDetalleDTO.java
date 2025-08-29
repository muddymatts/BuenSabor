package BuenSabor.dto.promocion;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDTO;
import BuenSabor.dto.articuloInsumo.ArticuloInsumoDTO;
import lombok.Data;

@Data
public class PromocionDetalleDTO {

    private Integer cantidad;
    private ArticuloInsumoDTO articuloInsumo;
    private ArticuloManufacturadoDTO articuloManufacturado;
}

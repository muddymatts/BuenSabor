package BuenSabor.dto.articuloManufacturado;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticulosManufacturadosDisponiblesDTO extends ArticuloManufacturadoDTO {
    private int cantidadDisponible;
}

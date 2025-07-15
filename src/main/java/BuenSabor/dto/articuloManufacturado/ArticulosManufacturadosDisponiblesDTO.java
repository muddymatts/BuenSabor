package BuenSabor.dto.articuloManufacturado;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class ArticulosManufacturadosDisponiblesDTO extends ArticuloManufacturadoDTO {
    private int cantidadDisponible;
}

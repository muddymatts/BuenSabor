package BuenSabor.mapper;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDTO;
import BuenSabor.model.ArticuloManufacturado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticuloManufacturadoMapper {

    ArticuloManufacturadoDTO toDTO (ArticuloManufacturado entity);

}

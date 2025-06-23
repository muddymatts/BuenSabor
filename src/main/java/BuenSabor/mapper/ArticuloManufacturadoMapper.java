package BuenSabor.mapper;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoResumenDTO;
import BuenSabor.model.ArticuloManufacturado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticuloManufacturadoMapper {

    ArticuloManufacturadoResumenDTO toDTO (ArticuloManufacturado entity);

}

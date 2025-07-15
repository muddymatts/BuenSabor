package BuenSabor.mapper;

import BuenSabor.dto.articuloManufacturado.ArticulosManufacturadosDisponiblesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductosMapper {

    ArticulosManufacturadosDisponiblesDTO toDTO(ArticulosManufacturadosDisponiblesDTO entity);

}

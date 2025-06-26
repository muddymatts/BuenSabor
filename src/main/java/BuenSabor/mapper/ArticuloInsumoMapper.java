package BuenSabor.mapper;

import BuenSabor.dto.articuloInsumo.ArticuloInsumoDTO;
import BuenSabor.model.ArticuloInsumo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticuloInsumoMapper {

    ArticuloInsumoDTO toDto(ArticuloInsumo entity);
    ArticuloInsumo toEntity(ArticuloInsumoDTO dto);
}

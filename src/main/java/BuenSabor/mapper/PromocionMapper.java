package BuenSabor.mapper;

import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.model.Promocion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring", uses = { PromocionDetalleMapper.class,
        ArticuloInsumoMapper.class,
        ArticuloManufacturadoMapper.class })
public interface PromocionMapper {

    PromocionDTO toDto(Promocion promocion);
    Promocion toEntity(PromocionDTO dto);
}

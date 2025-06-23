package BuenSabor.mapper;

import BuenSabor.dto.promocion.PromocionDetalleDTO;
import BuenSabor.model.PromocionDetalle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PromocionDetalleMapper {
    PromocionDetalleDTO toDto(PromocionDetalle entity);
    PromocionDetalle toEntity(PromocionDetalleDTO dto);
}

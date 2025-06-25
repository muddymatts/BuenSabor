package BuenSabor.mapper;

import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.model.Promocion;
import BuenSabor.model.PromocionDetalle;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring", uses = { PromocionDetalleMapper.class,
        ArticuloInsumoMapper.class,
        ArticuloManufacturadoMapper.class })
public interface PromocionMapper {


    PromocionDTO toDto(Promocion promocion);

    @AfterMapping
    default void calcularPrecioVenta (Promocion promocion, @MappingTarget PromocionDTO dto) {
        double precioVenta = 0;
        if(promocion.getDetalle() != null){
            for (PromocionDetalle pd : promocion.getDetalle()) {
                if(pd.getArticuloInsumo() != null){
                    precioVenta += pd.getCantidad()* pd.getArticuloInsumo().getPrecioVenta();
                } else if(pd.getArticuloManufacturado() != null){
                    precioVenta += pd.getCantidad() * pd.getArticuloManufacturado().getPrecioVenta();
                }
            }
            dto.setPrecioVenta(precioVenta*(1-promocion.getDescuento()));
        }
    }

    Promocion toEntity(PromocionDTO dto);
}

package BuenSabor.mapper;

import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.model.ImagenManufacturado;
import BuenSabor.model.Promocion;
import BuenSabor.model.PromocionDetalle;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring", uses = {
        PromocionDetalleMapper.class,
        ArticuloInsumoMapper.class,
        ArticuloManufacturadoMapper.class })
public interface PromocionMapper {


    PromocionDTO toDto(Promocion promocion);

    @AfterMapping
    default void setValoresCalculados(Promocion promocion, @MappingTarget PromocionDTO dto) {
        if(promocion.getDetalle() != null){
            double precioVenta = 0;
            for (PromocionDetalle pd : promocion.getDetalle()) {
                if(pd.getArticuloInsumo() != null){
                    if(pd.getArticuloInsumo().getImagenInsumo() != null){
                        dto.getImagenes().add(pd.getArticuloInsumo().getImagenInsumo().getDenominacion());
                    }
                    precioVenta += pd.getCantidad()* pd.getArticuloInsumo().getPrecioVenta();
                } else if(pd.getArticuloManufacturado() != null){
                    if(pd.getArticuloManufacturado().getImagenes() != null){
                        for (ImagenManufacturado imagen : pd.getArticuloManufacturado().getImagenes()){
                            dto.getImagenes().add(imagen.getDenominacion());
                        }
                    }
                    precioVenta += pd.getCantidad() * pd.getArticuloManufacturado().getPrecioVenta();
                }
            }
            dto.setPrecioSinDescuento(precioVenta);
            dto.setPrecioVenta(precioVenta*(1-promocion.getDescuento()));
        }
    }

    Promocion toEntity(PromocionDTO dto);
}

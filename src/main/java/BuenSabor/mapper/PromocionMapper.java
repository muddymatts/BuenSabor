package BuenSabor.mapper;

import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.model.ImagenManufacturado;
import BuenSabor.model.Promocion;
import BuenSabor.model.PromocionDetalle;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring", uses = {PromocionDetalleMapper.class})
public interface PromocionMapper {

    PromocionDTO toDto(Promocion promocion);

    @AfterMapping
    default void setValoresCalculados(Promocion promocion, @MappingTarget PromocionDTO dto) {
        //carga lista de imagenes
        if(promocion.getDetalle() != null){
            for (PromocionDetalle pd : promocion.getDetalle()) {
                if(pd.getArticuloInsumo() != null){
                    if(pd.getArticuloInsumo().getImagenInsumo() != null){
                        dto.getImagenes().add(pd.getArticuloInsumo().getImagenInsumo().getDenominacion());
                    }
                } else if(pd.getArticuloManufacturado() != null){
                    if(pd.getArticuloManufacturado().getImagenes() != null){
                        for (ImagenManufacturado imagen : pd.getArticuloManufacturado().getImagenes()){
                            dto.getImagenes().add(imagen.getDenominacion());
                        }
                    }
                }
            }
            dto.setPrecioSinDescuento(promocion.getPrecioSinDescuento());
            dto.setPrecioVenta(promocion.getPrecioVenta());
        }
    }

    Promocion toEntity(PromocionDTO dto);
}

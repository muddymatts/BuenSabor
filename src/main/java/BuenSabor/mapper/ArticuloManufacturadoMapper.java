package BuenSabor.mapper;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDTO;
import BuenSabor.model.ArticuloManufacturado;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticuloManufacturadoMapper {

    ArticuloManufacturadoDTO toDTO (ArticuloManufacturado entity);

    @AfterMapping
    default void setValoresCalculados(ArticuloManufacturado articuloManufacturado, @MappingTarget ArticuloManufacturadoDTO dto) {
        if(articuloManufacturado.getCategoria() != null) {
            dto.setNombreCategoria(articuloManufacturado.getCategoria().getDenominacion());
        }

        if(articuloManufacturado.getImagenes() != null){
            articuloManufacturado.getImagenes().forEach( imagenManufacturado ->
                    dto.getListaImagenes().add(imagenManufacturado.getDenominacion()));
        } else {
            dto.getListaImagenes().add("no imagen");
        }

        if(articuloManufacturado.getDetalles() != null) {
            articuloManufacturado.getDetalles().forEach( detalle -> {
                if(detalle.getInsumo() != null) {
                    dto.getIngredientes().add(detalle.getInsumo().getDenominacion());
                }
            });
        }
    }

}

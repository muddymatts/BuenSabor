package BuenSabor.mapper;

import BuenSabor.dto.articuloInsumo.ArticuloInsumoDTO;
import BuenSabor.model.ArticuloInsumo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticuloInsumoMapper {

    ArticuloInsumoDTO toDto(ArticuloInsumo entity);

    @AfterMapping
    default void setValoresCalculados(ArticuloInsumo articuloInsumo, @MappingTarget ArticuloInsumoDTO dto) {
        if(articuloInsumo.getImagenInsumo() != null){
            dto.setNombreImagen(articuloInsumo.getImagenInsumo().getDenominacion());
        } else {
            dto.setNombreImagen("no hay imagen");
        }
       if(articuloInsumo.getUnidadMedida() != null){
           dto.setNombreUnidadMedida(articuloInsumo.getUnidadMedida().getDenominacion());
       }
       dto.setCategorias(articuloInsumo.getCategoriaArticuloInsumo().getCategoriasAnidadas());
    }

    ArticuloInsumo toEntity(ArticuloInsumoDTO dto);
}

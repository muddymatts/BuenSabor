package BuenSabor.mapper;

import BuenSabor.dto.articuloInsumo.ArticuloInsumoDTO;
import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.CategoriaArticuloInsumo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

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
      dto.setCategorias(getCategoriasAnidadas(articuloInsumo.getCategoriaArticuloInsumo(),dto.getCategorias()));
    }

    private List<String> getCategoriasAnidadas(CategoriaArticuloInsumo categoria, List<String> categoriasAnidadas){
        if (categoria == null) return categoriasAnidadas;

        getCategoriasAnidadas(categoria.getCategoriaPadre(), categoriasAnidadas);

        categoriasAnidadas.add(categoria.getDenominacion());

        return categoriasAnidadas;
    }

    ArticuloInsumo toEntity(ArticuloInsumoDTO dto);
}

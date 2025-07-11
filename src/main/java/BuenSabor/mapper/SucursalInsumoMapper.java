package BuenSabor.mapper;

import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import BuenSabor.model.SucursalInsumo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SucursalInsumoMapper {

    @Mapping(source = "insumo.id", target = "idInsumo")
    @Mapping(source = "stockMinimo", target = "cantidadMinima")
    @Mapping(source = "stockMaximo", target = "cantidadMaxima")
    @Mapping(source = "stockActual", target = "cantidadActual")
    SucursalInsumoDTO toDTO(SucursalInsumo sucursalInsumo);


}

package BuenSabor.mapper;

import BuenSabor.dto.localidad.LocalidadDTO;
import BuenSabor.model.Localidad;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocalidadDTOMapper {

    public LocalidadDTO toDTO(Localidad localidad) {
        LocalidadDTO dto = new LocalidadDTO();
        dto.setId(localidad.getId());
        dto.setNombre(localidad.getNombre());
        return dto;
    }

    public List<LocalidadDTO> toDTOList(List<Localidad> localidades) {
        return localidades.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
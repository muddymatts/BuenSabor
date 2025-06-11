package BuenSabor.service;

import BuenSabor.dto.LocalidadDTO;
import BuenSabor.model.Localidad;
import BuenSabor.repository.LocalidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalidadService {

    private final LocalidadRepository localidadRepository;

    public LocalidadService(LocalidadRepository localidadRepository) {
        this.localidadRepository = localidadRepository;
    }

    public List<Localidad> findAll() {
        return localidadRepository.findAll();
    }

    public List<Localidad> findByProvinciaId(int provinciaId) {
        return localidadRepository.findByProvinciaId(provinciaId);
    }

    public List<LocalidadDTO> mapToDTOList(List<Localidad> localidades) {
        return localidades.stream()
                .map(localidad -> {
                    LocalidadDTO dto = new LocalidadDTO();
                    dto.setId(localidad.getId());
                    dto.setNombre(localidad.getNombre());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

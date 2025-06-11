package BuenSabor.service;

import BuenSabor.dto.localidad.LocalidadDTO;
import BuenSabor.mapper.LocalidadDTOMapper;
import BuenSabor.model.Localidad;
import BuenSabor.repository.LocalidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadService {

    private final LocalidadRepository localidadRepository;
    private final LocalidadDTOMapper localidadDTOMapper;

    public LocalidadService(LocalidadRepository localidadRepository, LocalidadDTOMapper localidadDTOMapper) {
        this.localidadRepository = localidadRepository;
        this.localidadDTOMapper = localidadDTOMapper;
    }

    public List<Localidad> findAll() {
        return localidadRepository.findAll();
    }

    public List<Localidad> findByProvinciaId(int provinciaId) {
        return localidadRepository.findByProvinciaId(provinciaId);
    }

    public List<LocalidadDTO> mapToDTOList(List<Localidad> localidades) {
        return localidadDTOMapper.toDTOList(localidades);
    }
}

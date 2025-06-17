package BuenSabor.service;

import BuenSabor.dto.localidad.LocalidadDTO;
import BuenSabor.mapper.LocalidadDTOMapper;
import BuenSabor.model.Localidad;
import BuenSabor.repository.LocalidadRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        List<Localidad> localidades = localidadRepository.findAll();
        localidades.sort(Comparator.comparing(Localidad::getNombre));
        return localidades;
    }

    public List<Localidad> findByProvinciaId(int provinciaId) {
        List<Localidad> localidades = localidadRepository.findByProvinciaId(provinciaId);
        localidades.sort(Comparator.comparing(Localidad::getNombre));
        return localidades;
    }

    public List<LocalidadDTO> mapToDTOList(List<Localidad> localidades) {
        return localidadDTOMapper.toDTOList(localidades);
    }
}
package BuenSabor.service;

import BuenSabor.model.Localidad;
import BuenSabor.repository.LocalidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

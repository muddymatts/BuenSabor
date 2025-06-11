package BuenSabor.service;

import BuenSabor.model.Provincia;
import BuenSabor.repository.PaisRepository;
import BuenSabor.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;
    private final PaisRepository paisRepository;

    public ProvinciaService(ProvinciaRepository provinciaRepository, PaisRepository paisRepository) {
        this.provinciaRepository = provinciaRepository;
        this.paisRepository = paisRepository;
    }

    public List<Provincia> findAll() {
        return provinciaRepository.findAll();
    }

    public List<Provincia> findProvinciasByPaisId(int id) {
        paisRepository.findById(id).orElseThrow(() ->
                new RuntimeException("El país con ID " + id + " no existe."));

        return provinciaRepository.findByPaisId(id);
    }
}

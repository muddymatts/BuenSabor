package BuenSabor.service;

import BuenSabor.model.Provincia;
import BuenSabor.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;

    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public List<Provincia> findAll() {
        return provinciaRepository.findAll();
    }

    public List<Provincia> findProvinciasByPaisId(int id) {
        return provinciaRepository.findByPaisId(id);
    }
}

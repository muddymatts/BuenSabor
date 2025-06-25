package BuenSabor.service;

import BuenSabor.model.Pais;
import BuenSabor.repository.PaisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisService {
    private final PaisRepository paisRepository;

    public PaisService(PaisRepository repository, PaisRepository paisRepository) {
        this.paisRepository = repository;
    }

    public List<Pais> findAll() {
        return paisRepository.findAll();
    }
}

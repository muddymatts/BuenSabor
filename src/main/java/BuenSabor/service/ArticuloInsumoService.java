package BuenSabor.service;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.repository.ArticuloInsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticuloInsumoService {

    private final ArticuloInsumoRepository repository;

    public ArticuloInsumoService(ArticuloInsumoRepository repository) {
        this.repository = repository;
    }

    public ArticuloInsumo crear(ArticuloInsumo insumo) {
        return repository.save(insumo);
    }

}
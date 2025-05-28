package BuenSabor.service;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.repository.ArticuloInsumoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ArticuloInsumoService {

    private final ArticuloInsumoRepository repository;

    public ArticuloInsumoService(ArticuloInsumoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ArticuloInsumo crear(ArticuloInsumo insumo) {
        return repository.save(insumo);
    }

    public ArticuloInsumo findById (Long id) {
        return repository.findByIdAndFechaBajaIsNull(id);
    }

}
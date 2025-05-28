package BuenSabor.service;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.repository.ArticuloInsumoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ArticuloInsumo> listarTodas() {
        return repository.findAll();
    }

}
package BuenSabor.service;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.repository.ArticuloManufacturadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepository repository;

    public ArticuloManufacturadoService(ArticuloManufacturadoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ArticuloManufacturado crear(ArticuloManufacturado articulo) {
        return repository.save(articulo);
    }
}

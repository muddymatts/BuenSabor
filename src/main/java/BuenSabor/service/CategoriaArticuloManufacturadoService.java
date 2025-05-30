package BuenSabor.service;

import BuenSabor.model.CategoriaArticuloManufacturado;
import BuenSabor.repository.CategoriaArticuloManufacturadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaArticuloManufacturadoService {

    private final CategoriaArticuloManufacturadoRepository Repository;

    public CategoriaArticuloManufacturadoService(CategoriaArticuloManufacturadoRepository Repository) {
        this.Repository = Repository;
    }

    @Transactional
    public CategoriaArticuloManufacturado crear(CategoriaArticuloManufacturado cat) {
        return Repository.save(cat);
    }
}

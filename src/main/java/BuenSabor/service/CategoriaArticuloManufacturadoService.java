package BuenSabor.service;

import BuenSabor.model.CategoriaArticuloManufacturado;
import BuenSabor.repository.CategoriaArticuloManufacturadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaArticuloManufacturadoService {

    private final CategoriaArticuloManufacturadoRepository repository;

    public CategoriaArticuloManufacturadoService(CategoriaArticuloManufacturadoRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaArticuloManufacturado> obtenerTodas() {
        return repository.findAll();
    }

    @Transactional
    public CategoriaArticuloManufacturado crear(CategoriaArticuloManufacturado cat) {
        return repository.save(cat);
    }
}

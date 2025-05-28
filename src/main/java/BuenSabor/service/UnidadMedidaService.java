package BuenSabor.service;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.UnidadMedida;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import BuenSabor.repository.UnidadMedidaRepository;

import java.util.List;

@Service
public class UnidadMedidaService {
    private final UnidadMedidaRepository repository;

    public UnidadMedidaService(UnidadMedidaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UnidadMedida crear(UnidadMedida unidadMedida) {
        return repository.save(unidadMedida);
    }

    public UnidadMedida buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Unidad no encontrada"));
    }

    public UnidadMedida actualizar(UnidadMedida unidad) {
        return repository.save(unidad);
    }

    public List<UnidadMedida> listarTodas() {
        return repository.findAll();
    }
}

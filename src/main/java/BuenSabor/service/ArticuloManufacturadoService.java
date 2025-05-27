package BuenSabor.service;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.ArticuloManufacturadoDetalle;
import BuenSabor.repository.ArticuloManufacturadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticuloManufacturadoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ArticuloManufacturadoRepository repository;

    public ArticuloManufacturadoService(ArticuloManufacturadoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ArticuloManufacturado crear(ArticuloManufacturado articulo) {
        //recorrer la lista para asignar el objeto padre.
        for (ArticuloManufacturadoDetalle detalle : articulo.getDetalles()) {
            detalle.setManufacturado(articulo);
            ArticuloInsumo insumo = entityManager.getReference(
                    ArticuloInsumo.class,
                    detalle.getInsumo().getId()
            );
            detalle.setInsumo(insumo);
        }

        return repository.save(articulo);
    }

    public ArticuloManufacturado buscarPorId(Long id) {
        return repository.getReferenceByIdAndFechaBajaIsNull(id);
    }

    public List<ArticuloManufacturado> findByFechaBajaIsNull() {
        return repository.findByFechaBajaIsNull();
    }
}

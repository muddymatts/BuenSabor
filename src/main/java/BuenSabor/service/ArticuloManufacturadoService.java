package BuenSabor.service;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.ArticuloManufacturadoDetalle;
import BuenSabor.repository.ArticuloManufacturadoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArticuloManufacturadoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ArticuloManufacturadoRepository repository;
    private final BajaLogicaService bajaLogicaService;

    public ArticuloManufacturadoService(ArticuloManufacturadoRepository repository,
                                        BajaLogicaService bajaLogicaService) {
        this.repository = repository;
        this.bajaLogicaService = bajaLogicaService;
    }

    @Transactional
    public ArticuloManufacturado guardar(ArticuloManufacturado articulo) {

        if(articulo.getImagenes().get(0).getDenominacion().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "El artículo debe tener al menos una imagen");
        }

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

    public List<ArticuloManufacturado> findAll() {
        return repository.findAll();
    }

    @Transactional
    public String eliminarArticuloManufacturado(Long id) {
        ArticuloManufacturado articulo = repository.getReferenceByIdAndFechaBajaIsNull(id);

        if (articulo == null) {
            throw new EntityNotFoundException("No se encontró el artículo manufacturado con ID: " + id);
        }

        bajaLogicaService.darDeBaja(ArticuloManufacturado.class, id);
        return articulo.getDenominacion();
    }
}

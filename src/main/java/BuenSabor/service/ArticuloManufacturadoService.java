package BuenSabor.service;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDTO;
import BuenSabor.mapper.ArticuloManufacturadoMapper;
import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.ArticuloManufacturadoDetalle;
import BuenSabor.model.ImagenManufacturado;
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
public class ArticuloManufacturadoService extends BajaLogicaService{

    @PersistenceContext
    private EntityManager entityManager;

    private final ArticuloManufacturadoRepository repository;
    private final ArticuloManufacturadoMapper mapper;

    public ArticuloManufacturadoService(ArticuloManufacturadoRepository repository,
                                        ArticuloManufacturadoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ArticuloManufacturado guardar(ArticuloManufacturado articulo) {

        double precioCosto = 0.0;

        if(!articulo.getImagenes().get(0).getDenominacion().isBlank()){
            for (ImagenManufacturado imagen : articulo.getImagenes()) {
                imagen.setArticuloManufacturado(articulo);
            }
        } else throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "El artículo debe tener al menos una imagen");

        for (ArticuloManufacturadoDetalle detalle : articulo.getDetalles()) {
            detalle.setManufacturado(articulo);
            ArticuloInsumo insumo = entityManager.getReference(
                    ArticuloInsumo.class,
                    detalle.getInsumo().getId()
            );
            precioCosto += insumo.getPrecioCompra() * detalle.getCantidad();
            detalle.setInsumo(insumo);
        }

        articulo.setPrecioCosto(precioCosto);

        return repository.save(articulo);
    }

    public ArticuloManufacturado getArticuloManufacturado(Long id) {
        return repository.getReferenceById(id);
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

        darDeBaja(ArticuloManufacturado.class, id);
        return articulo.getDenominacion();
    }

    public List<ArticuloManufacturadoDTO> getArticulosManufacturadoDTO() {
        List<ArticuloManufacturado> articulos = repository.findAll();
        return articulos.stream().map(articulo -> {
            ArticuloManufacturadoDTO articuloDTO = mapper.toDTO(articulo);
            articuloDTO.setNombreCategoria(articulo.getCategoria().getDenominacion());
            articulo.getImagenes().forEach(imagen -> {
                articuloDTO.getListaImagenes().add(imagen.getDenominacion());
            });
            articulo.getDetalles().forEach(detalle -> {
                articuloDTO.getIngredientes().add(detalle.getInsumo().getDenominacion());
            });
            return articuloDTO;
        }).toList();
    }
}

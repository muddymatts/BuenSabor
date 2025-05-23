package BuenSabor.service;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.ArticuloManufacturadoDetalle;
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
        //recorrer la lista para asignar el objeto padre.
        for (ArticuloManufacturadoDetalle detalle : articulo.getDetalles()) {
            detalle.setManufacturado(articulo);
        }


        return repository.save(articulo);
    }
}

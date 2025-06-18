package BuenSabor.service;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.EntityApp;
import BuenSabor.model.PedidoVenta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BajaLogicaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public <T extends EntityApp> void darDeBaja(Class<T> clazz, Long id) {
        T entidad = entityManager.find(clazz, id);
        if (entidad == null) {
            throw new EntityNotFoundException("No se encontró el recurso con ID: " + id);
        }
        if(entidad instanceof ArticuloManufacturado articulo){
            articulo.getDetalles().forEach(EntityApp::setFechaBaja);
        } else if (entidad instanceof PedidoVenta pedido){
            pedido.getDetalles().forEach(EntityApp::setFechaBaja);
        }
        entidad.setFechaBaja();
        entityManager.merge(entidad);
    }

}

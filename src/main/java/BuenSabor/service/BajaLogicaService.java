package BuenSabor.service;

import BuenSabor.model.*;
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
    public <T extends EntityApp> void darDeBaja(Class<T> tClass, Long id) {
        T entidad = entityManager.find(tClass, id);
        if (entidad == null) {
            throw new EntityNotFoundException("No se encontró el recurso con ID: " + id);
        }
        if(entidad.getFechaBaja() != null){
            throw new RuntimeException("La entidad ya ha sido dada de baja");
        }
        if(entidad instanceof ArticuloManufacturado articulo){
            articulo.getDetalles().forEach(EntityApp::setFechaBaja);
            articulo.getImagenes().forEach(EntityApp::setFechaBaja);
        } else if (entidad instanceof PedidoVenta pedido){
            pedido.getDetalles().forEach(EntityApp::setFechaBaja);
        } else if (entidad instanceof Promocion promocion){
            promocion.getDetalle().forEach(EntityApp::setFechaBaja);
        } else if (entidad instanceof ArticuloInsumo insumo){
            insumo.getImagenInsumo().setFechaBaja();
        }
        entidad.setFechaBaja();
        entityManager.merge(entidad);
    }

    @Transactional
    public <T extends EntityApp> void reestablecer (Class<T> tClass, Long id){
        T entidad = entityManager.find(tClass, id);
        if (entidad == null) {
            throw new EntityNotFoundException("No se encontró el recurso con ID: " + id);
        }
        if(entidad.getFechaBaja() == null){
            throw new RuntimeException("La entidad ya está activa");
        }
        if(entidad instanceof ArticuloManufacturado articulo){
            articulo.getDetalles().forEach(EntityApp::eliminarBaja);
            articulo.getImagenes().forEach(EntityApp::eliminarBaja);
        } else if (entidad instanceof PedidoVenta pedido){
            pedido.getDetalles().forEach(EntityApp::eliminarBaja);
        } else if (entidad instanceof Promocion promocion){
            promocion.getDetalle().forEach(EntityApp::eliminarBaja);
        } else if (entidad instanceof ArticuloInsumo insumo){
            insumo.getImagenInsumo().eliminarBaja();
        }
        entidad.eliminarBaja();
        entityManager.merge(entidad);
    }

}

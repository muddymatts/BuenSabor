package BuenSabor.service;

import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.dto.promocion.PromocionDetalleDTO;
import BuenSabor.enums.Estado;
import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.PedidoVenta;
import BuenSabor.model.PedidoVentaDetalle;
import BuenSabor.repository.PedidoVentaRepository;
import BuenSabor.service.articuloInsumo.ArticuloInsumoService;
import BuenSabor.service.promocion.PromocionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PedidoVentaService {

    private final PedidoVentaRepository repository;
    private final BajaLogicaService bajaLogicaService;
    private final ArticuloManufacturadoService articuloManufacturadoService;
    private final ArticuloInsumoService articuloInsumoService;
    private final PromocionService promocionService;

    public PedidoVentaService(
            PedidoVentaRepository repository,
            BajaLogicaService bajaLogicaService,
            ArticuloManufacturadoService articuloManufacturadoService,
            ArticuloInsumoService articuloInsumoService, PromocionService promocionService) {
        this.repository = repository;
        this.bajaLogicaService = bajaLogicaService;
        this.articuloManufacturadoService = articuloManufacturadoService;
        this.articuloInsumoService = articuloInsumoService;
        this.promocionService = promocionService;
    }

    public List<PedidoVenta> listarTodas() {
        return repository.findAll();
    }

    @Transactional
    public PedidoVenta crear(PedidoVenta pedido) {

        //asigno hora y fecha local de pedido
        LocalDateTime fechaHoraPedido = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        pedido.setFechaHoraPedido(fechaHoraPedido);

        //Asigno estado inicial
        pedido.setEstado(Estado.preparacion);

        //Crear y cargar los detalles
        if(!pedido.getDetalles().isEmpty()){
            for(PedidoVentaDetalle detalle : pedido.getDetalles()){
                detalle.setPedido(pedido);
            }
        }

        pedido.setHoraEstimadaFinalizacion(CalcularDemoraTotal(pedido));

        pedido.setCostoTotal(CalcularCostoTotal(pedido));

        return repository.save(pedido);
    }

    private BigDecimal CalcularCostoTotal(PedidoVenta pedido) {
        double costoTotal = 0.0;
        for(PedidoVentaDetalle detalle : pedido.getDetalles()) {
            if (detalle.getArticuloManufacturado() != null) {
                double costo = articuloManufacturadoService.getArticuloManufacturado(detalle.getArticuloManufacturado().getId()).getPrecioCosto();
                costoTotal += costo * detalle.getCantidad();
            }
            if (detalle.getArticuloInsumo() != null) {
                double costo = articuloInsumoService.findById(detalle.getArticuloInsumo().getId()).getPrecioCompra();
                costoTotal += costo * detalle.getCantidad();
            }
        }
        return BigDecimal.valueOf(costoTotal);
    }

    private String CalcularDemoraTotal (PedidoVenta pedido) {
        LocalDateTime horaFinalizacion = pedido.getFechaHoraPedido();
        long demoraTotal = 0;
        for(PedidoVentaDetalle detallePedido : pedido.getDetalles()){
            if(detallePedido.getArticuloManufacturado() != null){
                ArticuloManufacturado articulo = articuloManufacturadoService.getArticuloManufacturado(detallePedido.getArticuloManufacturado().getId());
                demoraTotal += articulo.getTiempoEstimado() * (long)detallePedido.getCantidad();
            } else if (detallePedido.getPromocion() != null) {
                PromocionDTO promocion = promocionService.buscarPorId(detallePedido.getPromocion().getId());
                long demoraPromocion = 0;
                for (PromocionDetalleDTO pd : promocion.getDetalle()){
                    if (pd.getArticuloManufacturado() == null) continue;
                    ArticuloManufacturado articulo = articuloManufacturadoService.getArticuloManufacturado(pd.getArticuloManufacturado().getId());
                    demoraPromocion += articulo.getTiempoEstimado() * (long)pd.getCantidad();
                }
                demoraTotal += demoraPromocion * (long)detallePedido.getCantidad();
            }
        }
        return horaFinalizacion.plusMinutes(demoraTotal).toString();
    }

    public String darDeBaja(Long id) {
        PedidoVenta pedido = repository.getReferenceById(id);

        if (pedido == null) {
            throw new EntityNotFoundException("No se encontró el artículo manufacturado con ID: " + id);
        }

        bajaLogicaService.darDeBaja(PedidoVenta.class, id);

        return pedido.getId().toString();
    }

    public PedidoVenta buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id));
    }
}

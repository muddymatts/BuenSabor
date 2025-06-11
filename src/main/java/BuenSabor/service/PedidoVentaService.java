package BuenSabor.service;

import BuenSabor.enums.Estado;
import BuenSabor.model.PedidoVenta;
import BuenSabor.model.PedidoVentaDetalle;
import BuenSabor.repository.PedidoVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class PedidoVentaService {

    private final PedidoVentaRepository repository;
    private final BajaLogicaService bajaLogicaService;
    private final ArticuloManufacturadoService articuloManufacturadoService;
    private final ArticuloInsumoService articuloInsumoService;

    public PedidoVentaService(
            PedidoVentaRepository repository,
            BajaLogicaService bajaLogicaService,
            ArticuloManufacturadoService articuloManufacturadoService,
            ArticuloInsumoService articuloInsumoService) {
        this.repository = repository;
        this.bajaLogicaService = bajaLogicaService;
        this.articuloManufacturadoService = articuloManufacturadoService;
        this.articuloInsumoService = articuloInsumoService;
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
                double costo = articuloManufacturadoService.buscarPorId(detalle.getArticuloManufacturado().getId()).getPrecioCosto();
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
        for(PedidoVentaDetalle detalle : pedido.getDetalles()){
            if(detalle.getArticuloManufacturado() != null){
                long demora = articuloManufacturadoService.buscarPorId(detalle.getArticuloManufacturado().getId()).getTiempoEstimado();
                demoraTotal += demora * (long)detalle.getCantidad();
            }
            //TODO agregar la demora de la "promocion"
        }
        return horaFinalizacion.plusMinutes(demoraTotal).toString();
    }

}

package BuenSabor.service.pedido;

import BuenSabor.dto.pedido.PedidoVentaDTO;
import BuenSabor.dto.response.ResponseDTO;
import BuenSabor.enums.Estado;
import BuenSabor.mapper.PedidoVentaMapper;
import BuenSabor.model.*;
import BuenSabor.repository.PedidoVentaRepository;
import BuenSabor.service.ArticuloManufacturadoService;
import BuenSabor.service.BajaLogicaService;
import BuenSabor.service.articuloInsumo.ArticuloInsumoService;
import BuenSabor.service.promocion.PromocionService;
import BuenSabor.service.sucursal.SucursalEmpresaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class PedidoVentaService extends BajaLogicaService {

    private final PedidoVentaRepository pedidoVentaRepository;
    private final ArticuloManufacturadoService articuloManufacturadoService;
    private final ArticuloInsumoService articuloInsumoService;
    private final PedidoVentaMapper pedidoVentaMapper;
    private final SucursalEmpresaService sucursalEmpresaService;
    private final PromocionService promocionService;

    public PedidoVentaService(
            PedidoVentaRepository repository,
            ArticuloManufacturadoService articuloManufacturadoService,
            ArticuloInsumoService articuloInsumoService,
            PedidoVentaMapper pedidoVentaMapper,
            SucursalEmpresaService sucursalEmpresaService, PromocionService promocionService) {
        this.pedidoVentaRepository = repository;
        this.articuloManufacturadoService = articuloManufacturadoService;
        this.articuloInsumoService = articuloInsumoService;
        this.pedidoVentaMapper = pedidoVentaMapper;
        this.sucursalEmpresaService = sucursalEmpresaService;
        this.promocionService = promocionService;
    }

    public Page<?> listarTodas(Pageable pageable) {
        return pedidoVentaRepository.findAll(pageable)
                .map(pedidoVentaMapper::toDto);
    }

    @Transactional
    public ResponseDTO crear(PedidoVentaDTO pedidodto) {

        PedidoVenta pedido = pedidoVentaMapper.toEntity(pedidodto);

        if (pedido.getCliente() == null) throw new RuntimeException("El pedido debe tener un cliente");
        if (pedido.getSucursalEmpresa() == null) throw new RuntimeException("El pedido debe tener una sucursal");

        //Crear y cargar los detalles
        if (!pedido.getDetalles().isEmpty()) {
            for (PedidoVentaDetalle detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);
            }
            if(!ReducirStok(pedido)) throw new RuntimeException("Hubo un problema de stock");
        } else throw new RuntimeException("El pedido debe tener al menos un detalle");

        //asigno hora y fecha local de pedido
        pedido.setFechaHoraPedido(LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires")));

        //Asigno estado inicial
        pedido.setEstado(Estado.pendiente);

        pedido.setHoraEstimadaFinalizacion(CalcularDemoraTotal(pedido));

        pedido.setCostoTotal(CalcularCostoTotal(pedido));

        PedidoVenta nuevoPedido = pedidoVentaRepository.save(pedido);

        return new ResponseDTO("Pedido creado exitosamente", nuevoPedido.getId());
    }

    @Transactional
    protected Boolean ReducirStok(PedidoVenta pedido){
        for(PedidoVentaDetalle detalle : pedido.getDetalles()){
            if(detalle.getArticuloManufacturado() != null){
                ArticuloManufacturado articulo = articuloManufacturadoService.getArticuloManufacturado(detalle.getArticuloManufacturado().getId());
                for(ArticuloManufacturadoDetalle detalleManufacturado : articulo.getDetalles()){
                    sucursalEmpresaService.removeStock(
                            pedido.getSucursalEmpresa().getId(),
                            detalleManufacturado.getInsumo().getId(),
                            detalleManufacturado.getCantidad()*detalle.getCantidad());
                }
            } else if(detalle.getArticuloInsumo() != null){
                sucursalEmpresaService.removeStock(
                        pedido.getSucursalEmpresa().getId(),
                        detalle.getArticuloInsumo().getId(),
                        detalle.getCantidad());
            } else if(detalle.getPromocion() != null){
                Promocion promocion = promocionService.findById(detalle.getPromocion().getId());
                for(PromocionDetalle detallePromo : promocion.getDetalle()){
                    if(detallePromo.getArticuloManufacturado() != null){
                        ArticuloManufacturado articulo = articuloManufacturadoService.getArticuloManufacturado(detallePromo.getArticuloManufacturado().getId());
                        for(ArticuloManufacturadoDetalle detalleManufacturado : articulo.getDetalles()){
                            sucursalEmpresaService.removeStock(
                                    pedido.getSucursalEmpresa().getId(),
                                    detalleManufacturado.getInsumo().getId(),
                                    detalleManufacturado.getCantidad() * detallePromo.getCantidad() * detalle.getCantidad());
                        }
                    } else if (detallePromo.getArticuloInsumo() != null){
                        sucursalEmpresaService.removeStock(
                                pedido.getSucursalEmpresa().getId(),
                                detallePromo.getArticuloInsumo().getId(),
                                detallePromo.getCantidad() * detalle.getCantidad());
                    }
                }
            }
        }
        return true;
    }

    private BigDecimal CalcularCostoTotal(PedidoVenta pedido) {
        double costoTotal = 0.0;
        for (PedidoVentaDetalle detalle : pedido.getDetalles()) {
            if (detalle.getArticuloManufacturado() != null) {
                double costo = articuloManufacturadoService.getArticuloManufacturado(detalle.getArticuloManufacturado().getId()).getPrecioCosto();
                costoTotal += costo * detalle.getCantidad();
            }
            if (detalle.getArticuloInsumo() != null) {
                double costo = articuloInsumoService.findById(detalle.getArticuloInsumo().getId()).getPrecioCompra();
                costoTotal += costo * detalle.getCantidad();
            }
            if (detalle.getPromocion() != null) {
                double costo = detalle.getPromocion().getPrecioCosto();
                costoTotal += costo * detalle.getCantidad();
            }
        }
        return BigDecimal.valueOf(costoTotal);
    }

    private String CalcularDemoraTotal(PedidoVenta pedido) {
        LocalDateTime horaFinalizacion = pedido.getFechaHoraPedido();
        long demoraTotal = 0;
        for (PedidoVentaDetalle detallePedido : pedido.getDetalles()) {
            if (detallePedido.getArticuloManufacturado() != null) {
                ArticuloManufacturado articulo = articuloManufacturadoService.getArticuloManufacturado(detallePedido.getArticuloManufacturado().getId());
                demoraTotal += articulo.getTiempoEstimado() * (long) detallePedido.getCantidad();
            } else if (detallePedido.getPromocion() != null) {
                Promocion promocion = promocionService.findById(detallePedido.getPromocion().getId());
                demoraTotal += promocion.getDemoraTotal() * (long) detallePedido.getCantidad();
            }
        }
        return horaFinalizacion.plusMinutes(demoraTotal).toString();
    }

    public PedidoVentaDTO buscarPorId(Long id) {
        return pedidoVentaMapper.toDto(pedidoVentaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id)));
    }

    public PedidoVenta actualizarEstado(Long id, Estado estado) {
        PedidoVenta pedido = pedidoVentaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id));
        pedido.setEstado(estado);
        return pedidoVentaRepository.save(pedido);
    }

    public List<PedidoVentaDTO> getPedidosDTO() {
        return pedidoVentaRepository.findAll().stream()
                .map(pedidoVentaMapper::toDto)
                .toList();
    }

    public Page<?> getPedidosFiltradosDTO(Long idCliente, Long idSucursal, Pageable pageable) {
        Page<PedidoVenta> page = filterPedidos(idCliente, idSucursal, pageable);
        return page.map(pedidoVentaMapper::toDto);
    }

    private Page<PedidoVenta> filterPedidos(Long idCliente, Long idSucursal, Pageable pageable) {
        if (idCliente != null && idSucursal != null) {
            return pedidoVentaRepository.findByClienteIdAndSucursalEmpresaId(idCliente, idSucursal, pageable);
        } else if (idCliente != null) {
            return pedidoVentaRepository.findByClienteId(idCliente, pageable);
        } else if (idSucursal != null) {
            return pedidoVentaRepository.findBySucursalEmpresaId(idSucursal, pageable);
        } else {
            return pedidoVentaRepository.findAll(pageable);
        }
    }
}

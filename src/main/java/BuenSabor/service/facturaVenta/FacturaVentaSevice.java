package BuenSabor.service.facturaVenta;

import BuenSabor.enums.FormaPago;
import BuenSabor.model.*;
import BuenSabor.repository.PedidoVentaRepository;
import BuenSabor.repository.facturaVenta.FacturaVentaRepository;
import BuenSabor.service.BajaLogicaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class FacturaVentaSevice extends BajaLogicaService {

    private final FacturaVentaRepository facturaVentaRepository;
    private final PedidoVentaRepository pedidoVentaRepository;

    public FacturaVentaSevice(FacturaVentaRepository facturaVentaRepository,
                              PedidoVentaRepository pedidoVentaRepository) {
        this.facturaVentaRepository = facturaVentaRepository;
        this.pedidoVentaRepository = pedidoVentaRepository;
    }

    public FacturaVenta getFactura (Long id) {
        return facturaVentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }


    @Transactional
    public FacturaVenta facturarPedido (Long idPedido, FormaPago formaPago) {
        PedidoVenta pedido = pedidoVentaRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        if (pedido.getFacturaVenta() != null) throw new RuntimeException("El pedido ya tiene una factura");
        FacturaVenta factura = new FacturaVenta();
        factura.setFormaPago(formaPago);
        factura.setFechaFacturacion(LocalDate.now());
        factura.setCliente(pedido.getCliente());
        factura.setNumeroComprobante(generarComprobante(pedido));
        BigDecimal descuentoFactura = BigDecimal.ZERO;
        BigDecimal gastoEnvio = BigDecimal.ZERO;
        if (pedido.getGastosEnvio() != null) gastoEnvio = pedido.getGastosEnvio();
        factura.setGastosEnvio(gastoEnvio);
        factura.setDetalles(new ArrayList<>());
        BigDecimal subtotalFactura = BigDecimal.ZERO;
        if(pedido.getDetalles()!=null){
            for(PedidoVentaDetalle pedidoVentaDetalle : pedido.getDetalles()){
                if (pedidoVentaDetalle.getPromocion() != null) {
                    double precioConDescuentoPromo = pedidoVentaDetalle.getPromocion().getPrecioSinDescuento() - pedidoVentaDetalle.getPromocion().getPrecioVenta();
                    descuentoFactura = descuentoFactura.add(BigDecimal.valueOf(precioConDescuentoPromo * pedidoVentaDetalle.getCantidad()));
                    for (PromocionDetalle detallePromo : pedidoVentaDetalle.getPromocion().getDetalle()) {
                        FacturaVentaDetalle detalleFacturaPromo = new FacturaVentaDetalle();
                        detalleFacturaPromo.setFacturaVenta(factura);
                        detalleFacturaPromo.setCantidad(detallePromo.getCantidad()*(int)pedidoVentaDetalle.getCantidad());
                        if (detallePromo.getArticuloInsumo() != null) {
                            detalleFacturaPromo.setSubtotal(BigDecimal.valueOf(detallePromo.getArticuloInsumo().getPrecioVenta()*detallePromo.getCantidad() * pedidoVentaDetalle.getCantidad()));
                            detalleFacturaPromo.setArticuloInsumo(detallePromo.getArticuloInsumo());
                            factura.getDetalles().add(detalleFacturaPromo);
                        } else if (detallePromo.getArticuloManufacturado() != null) {
                            detalleFacturaPromo.setSubtotal(BigDecimal.valueOf(detallePromo.getArticuloManufacturado().getPrecioVenta() * detallePromo.getCantidad() * pedidoVentaDetalle.getCantidad()));
                            detalleFacturaPromo.setArticuloManufacturado(detallePromo.getArticuloManufacturado());
                            factura.getDetalles().add(detalleFacturaPromo);
                        }
                        subtotalFactura = subtotalFactura.add(detalleFacturaPromo.getSubtotal());
                    }
                } else {
                    FacturaVentaDetalle detalleFactura = new FacturaVentaDetalle();
                    if(pedidoVentaDetalle.getArticuloInsumo()!=null){
                        detalleFactura.setFacturaVenta(factura);
                        detalleFactura.setCantidad(pedidoVentaDetalle.getCantidad());
                        detalleFactura.setSubtotal(BigDecimal.valueOf(pedidoVentaDetalle.getSubtotal()));
                        detalleFactura.setArticuloInsumo(pedidoVentaDetalle.getArticuloInsumo());
                        factura.getDetalles().add(detalleFactura);
                    } else if (pedidoVentaDetalle.getArticuloManufacturado() != null){
                        detalleFactura.setFacturaVenta(factura);
                        detalleFactura.setCantidad(pedidoVentaDetalle.getCantidad());
                        detalleFactura.setSubtotal(BigDecimal.valueOf(pedidoVentaDetalle.getSubtotal()));
                        detalleFactura.setArticuloManufacturado(pedidoVentaDetalle.getArticuloManufacturado());
                        factura.getDetalles().add(detalleFactura);
                    }
                    subtotalFactura = subtotalFactura.add(detalleFactura.getSubtotal());
                    factura.getDetalles().add(detalleFactura);
                }
            }
        }
        factura.setDescuento(descuentoFactura);
        factura.setSubtotal(subtotalFactura);
        factura.setTotalVenta(subtotalFactura.add(factura.getGastosEnvio()).subtract(factura.getDescuento()));
        pedido.setFacturaVenta(factura);
        factura.setPedidos(new ArrayList<>());
        factura.getPedidos().add(pedido);
        return facturaVentaRepository.save(factura);
    }

    private String generarComprobante(PedidoVenta pedido) {
        String comprobante = "";
        comprobante += pedido.getSucursalEmpresa().getId();
        comprobante += LocalDateTime.now().getYear();
        comprobante += LocalDateTime.now().getMonthValue();
        comprobante += LocalDateTime.now().getDayOfMonth();
        comprobante += pedido.getId();
        return comprobante;
    }
}

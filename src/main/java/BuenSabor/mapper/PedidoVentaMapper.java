package BuenSabor.mapper;

import BuenSabor.dto.pedido.PedidoVentaDTO;
import BuenSabor.model.PedidoVenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PedidoVentaDetalleMapper.class})
public interface PedidoVentaMapper {

    @Mapping(source = "sucursalEmpresa.id", target = "idSucursal")
    @Mapping(source = "cliente.id", target = "idCliente")
    @Mapping(source = "cliente.domicilio.id", target = "idDireccionEntrega")
    @Mapping(source = "facturaVenta.id", target = "idFactura")
    @Mapping(source = "id", target = "idPedido")
    @Mapping(source = "estado", target = "estadoPedido")
    @Mapping(source = "fechaHoraPedido", target = "fechaCreacion")
    PedidoVentaDTO toDto(PedidoVenta pedidoVenta);
    PedidoVenta toEntity(PedidoVentaDTO pedidoDTO);
}



package BuenSabor.mapper;

import BuenSabor.dto.pedidoVenta.*;
import BuenSabor.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoVentaMapper {

    @Mapping(target = "sucursalEmpresa", source = "sucursalEmpresa")
    @Mapping(target = "cliente", source = "cliente")
    @Mapping(target = "detalles", source = "detalles")
    PedidoVentaResumenDTO toResumenDTO(PedidoVenta pedido);

    SucursalEmpresaDTO toDTO(SucursalEmpresa sucursal);

    ClienteVentaDTO toClienteVentaDTO(Cliente cliente);

    @Mapping(target = "articuloManufacturado", source = "articuloManufacturado")
    DetalleVentaDTO toDetalleDTO(PedidoVentaDetalle detalle);

    @Mapping(target = "categoria", source = "categoriaId")
    ArticuloManufVentaDTO toArticuloManufVentaDTO(ArticuloManufacturado articulo);
}

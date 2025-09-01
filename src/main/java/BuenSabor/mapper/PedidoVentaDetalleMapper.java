package BuenSabor.mapper;

import BuenSabor.dto.pedido.PedidoVentaDetalleDTO;
import BuenSabor.model.PedidoVentaDetalle;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {ArticuloInsumoMapper.class, ArticuloManufacturadoMapper.class, PromocionMapper.class})
public interface PedidoVentaDetalleMapper {

     PedidoVentaDetalleDTO toDto(PedidoVentaDetalle entity);

     @AfterMapping
    default void setearValoresCalculados (PedidoVentaDetalle pedidoVentaDetalle, @MappingTarget PedidoVentaDetalleDTO dto){
        if(pedidoVentaDetalle.getPromocion() != null){
            dto.setTipoItem("Promocion");
            dto.getCategorias().add("Promocion");
            dto.setDenominacion(pedidoVentaDetalle.getPromocion().getDenominacion());
            dto.setItemId(pedidoVentaDetalle.getPromocion().getId());
            dto.setPrecioVenta(BigDecimal.valueOf(pedidoVentaDetalle.getPromocion().getPrecioVenta()));
        } else if (pedidoVentaDetalle.getArticuloInsumo() != null){
            dto.setTipoItem("ArticuloInsumo");
            dto.setCategorias(pedidoVentaDetalle.getArticuloInsumo().getCategoriaArticuloInsumo().getCategoriasAnidadas());
            dto.setDenominacion(pedidoVentaDetalle.getArticuloInsumo().getDenominacion());
            dto.setPrecioVenta(BigDecimal.valueOf(pedidoVentaDetalle.getArticuloInsumo().getPrecioVenta()));
            dto.setItemId(pedidoVentaDetalle.getArticuloInsumo().getId());
            dto.setPrecioVenta(BigDecimal.valueOf(pedidoVentaDetalle.getArticuloInsumo().getPrecioVenta()));
        } else if (pedidoVentaDetalle.getArticuloManufacturado() != null){
            dto.setTipoItem("ArticuloManufacturado");
            dto.setDenominacion(pedidoVentaDetalle.getArticuloManufacturado().getDenominacion());
            dto.getCategorias().add(pedidoVentaDetalle.getArticuloManufacturado().getCategoria().getDenominacion());
            dto.setItemId(pedidoVentaDetalle.getArticuloManufacturado().getId());
            dto.setPrecioVenta(BigDecimal.valueOf(pedidoVentaDetalle.getArticuloManufacturado().getPrecioVenta()));
        }
     }
}

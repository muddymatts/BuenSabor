package BuenSabor.mapper;

import BuenSabor.dto.pedido.PedidoVentaDetalleDTO;
import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.PedidoVentaDetalle;
import BuenSabor.model.Promocion;
import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface PedidoVentaDetalleMapper {

    // ====== ENTITY -> DTO ======
    PedidoVentaDetalleDTO toDto(PedidoVentaDetalle entity);

    // ====== DTO -> ENTITY ======
    @Mapping(source = "cantidad", target = "cantidad")
    @Mapping(source = "subTotal", target = "subTotal")
    @Mapping(source = "itemId", target = "articuloManufacturado", qualifiedByName = "mapArticuloManufacturado")
    @Mapping(source = "itemId", target = "articuloInsumo", qualifiedByName = "mapArticuloInsumo")
    @Mapping(source = "itemId", target = "promocion", qualifiedByName = "mapPromocion")
    PedidoVentaDetalle toEntity(PedidoVentaDetalleDTO dto);

    // ====== AfterMapping para limpiar entidades según tipo ======
    @AfterMapping
    default void ajustarTipoItem(PedidoVentaDetalleDTO dto, @MappingTarget PedidoVentaDetalle entity) {
        switch (dto.getTipoItem()) {
            case "ArticuloManufacturado":
                entity.setArticuloInsumo(null);
                entity.setPromocion(null);
                break;
            case "ArticuloInsumo":
                entity.setArticuloManufacturado(null);
                entity.setPromocion(null);
                break;
            case "Promocion":
                entity.setArticuloManufacturado(null);
                entity.setArticuloInsumo(null);
                break;
            default:
                entity.setArticuloManufacturado(null);
                entity.setArticuloInsumo(null);
                entity.setPromocion(null);
        }
    }

    // ====== Métodos auxiliares ======

    @Named("mapArticuloManufacturado")
    default ArticuloManufacturado mapArticuloManufacturado(Long id) {
        if (id == null) return null;
        ArticuloManufacturado art = new ArticuloManufacturado();
        art.setId(id);
        return art;
    }

    @Named("mapArticuloInsumo")
    default ArticuloInsumo mapArticuloInsumo(Long id) {
        if (id == null) return null;
        ArticuloInsumo insumo = new ArticuloInsumo();
        insumo.setId(id);
        return insumo;
    }

    @Named("mapPromocion")
    default Promocion mapPromocion(Long id) {
        if (id == null) return null;
        Promocion promo = new Promocion();
        promo.setId(id);
        return promo;
    }

    // ====== AfterMapping para calcular valores DTO ======
    @AfterMapping
    default void setearValoresCalculados(PedidoVentaDetalle entity, @MappingTarget PedidoVentaDetalleDTO dto) {
        if (entity.getPromocion() != null) {
            dto.setTipoItem("Promocion");
            dto.getCategorias().add("Promocion");
            dto.setDenominacion(entity.getPromocion().getDenominacion());
            dto.setItemId(entity.getPromocion().getId());
            dto.setPrecioVenta(BigDecimal.valueOf(entity.getPromocion().getPrecioVenta()));
        } else if (entity.getArticuloInsumo() != null) {
            dto.setTipoItem("ArticuloInsumo");
            dto.setCategorias(entity.getArticuloInsumo().getCategoriaArticuloInsumo().getCategoriasAnidadas());
            dto.setDenominacion(entity.getArticuloInsumo().getDenominacion());
            dto.setPrecioVenta(BigDecimal.valueOf(entity.getArticuloInsumo().getPrecioVenta()));
            dto.setItemId(entity.getArticuloInsumo().getId());
        } else if (entity.getArticuloManufacturado() != null) {
            dto.setTipoItem("ArticuloManufacturado");
            dto.setDenominacion(entity.getArticuloManufacturado().getDenominacion());
            dto.getCategorias().add(entity.getArticuloManufacturado().getCategoria().getDenominacion());
            dto.setItemId(entity.getArticuloManufacturado().getId());
            dto.setPrecioVenta(BigDecimal.valueOf(entity.getArticuloManufacturado().getPrecioVenta()));
        }
    }
}


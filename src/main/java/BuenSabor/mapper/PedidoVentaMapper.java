package BuenSabor.mapper;

import BuenSabor.dto.pedido.PedidoVentaDTO;
import BuenSabor.enums.Estado;
import BuenSabor.enums.TipoEnvio;
import BuenSabor.model.Cliente;
import BuenSabor.model.FacturaVenta;
import BuenSabor.model.PedidoVenta;
import BuenSabor.model.SucursalEmpresa;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {PedidoVentaDetalleMapper.class})
public interface PedidoVentaMapper {

    // ====== ENTITY -> DTO ======
    @Mapping(source = "sucursalEmpresa.id", target = "idSucursal")
    @Mapping(source = "cliente.id", target = "idCliente")
    @Mapping(source = "cliente.domicilio.id", target = "idDireccionEntrega")
    @Mapping(source = "facturaVenta.id", target = "idFactura")
    @Mapping(source = "id", target = "idPedido")
    @Mapping(source = "estado", target = "estadoPedido")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio")
    @Mapping(source = "fechaHoraPedido", target = "fechaCreacion")
    PedidoVentaDTO toDto(PedidoVenta pedidoVenta);


    // ====== DTO -> ENTITY ======
    @Mapping(source = "idPedido", target = "id")
    @Mapping(source = "estadoPedido", target = "estado", qualifiedByName = "mapEstado")
    @Mapping(source = "tipoEnvio", target = "tipoEnvio", qualifiedByName = "mapTipoEnvio")
    @Mapping(source = "fechaCreacion", target = "fechaHoraPedido", qualifiedByName = "mapFechaHora")
    @Mapping(source = "idSucursal", target = "sucursalEmpresa", qualifiedByName = "mapSucursal")
    @Mapping(source = "idCliente", target = "cliente", qualifiedByName = "mapCliente")
    @Mapping(source = "idFactura", target = "facturaVenta", qualifiedByName = "mapFactura")
    PedidoVenta toEntity(PedidoVentaDTO pedidoDTO);


    // ====== Métodos auxiliares ======

    @Named("mapSucursal")
    default SucursalEmpresa mapSucursal(Long id) {
        if (id == null) return null;
        SucursalEmpresa s = new SucursalEmpresa();
        s.setId(id);
        return s;
    }

    @Named("mapCliente")
    default Cliente mapCliente(Long id) {
        if (id == null) return null;
        Cliente c = new Cliente();
        c.setId(id);
        return c;
    }

    @Named("mapFactura")
    default FacturaVenta mapFactura(Long id) {
        if (id == null) return null;
        FacturaVenta f = new FacturaVenta();
        f.setId(id);
        return f;
    }

    @Named("mapEstado")
    default Estado mapEstado(String estado) {
        return estado != null ? Estado.valueOf(estado) : null;
    }

    @Named("mapTipoEnvio")
    default TipoEnvio mapTipoEnvio(String tipoEnvio) {
        return tipoEnvio != null ? TipoEnvio.valueOf(tipoEnvio) : null;
    }

    @Named("mapFechaHora")
    default LocalDateTime mapFechaHora(LocalDate fecha) {
        // Convierte LocalDate a LocalDateTime con hora 00:00
        return fecha != null ? fecha.atStartOfDay() : null;
    }

    // ====== AfterMapping para mantener consistencia padre-hijo ======
    @AfterMapping
    default void linkDetalles(@MappingTarget PedidoVenta entity) {
        if (entity.getDetalles() != null) {
            entity.getDetalles().forEach(detalle -> detalle.setPedido(entity));
        }
    }
}




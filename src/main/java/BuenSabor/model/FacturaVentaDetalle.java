package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class FacturaVentaDetalle extends EntityApp {

    private Integer cantidad;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "factura_venta_id")
    private FacturaVenta facturaVenta;

    @ManyToOne
    @JoinColumn(name = "articulo_manufacturado_id")
    private ArticuloManufacturado articuloManufacturado;

    @ManyToOne
    @JoinColumn(name = "articulo_insumo_id")
    private ArticuloInsumo articuloInsumo;
}

package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PedidoVentaDetalle extends EntityApp {

    private double cantidad;
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "pedido_venta_id")
    private PedidoVenta pedido;

    @OneToOne
    @JoinColumn(name = "articulo_manufacturado_id")
    private ArticuloManufacturado articuloManufacturado;

    @OneToOne
    @JoinColumn(name = "articulo_insumo_id")
    private ArticuloInsumo articuloInsumo;

    @OneToOne
    @JoinColumn(name = "promocion_id")
    private Promocion promocion;
}

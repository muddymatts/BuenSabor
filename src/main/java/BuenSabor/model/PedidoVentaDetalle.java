package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @JsonBackReference
    private PedidoVenta pedido;

    @ManyToOne
    @JoinColumn(name = "articulo_manufacturado_id")
    private ArticuloManufacturado articuloManufacturado;

    @ManyToOne
    @JoinColumn(name = "articulo_insumo_id")
    private ArticuloInsumo articuloInsumo;

    @ManyToOne
    @JoinColumn(name = "promocion_id")
    private Promocion promocion;
}

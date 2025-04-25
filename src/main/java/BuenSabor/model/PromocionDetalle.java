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
public class PromocionDetalle extends EntityApp {
    private Integer cantidad;

    @OneToOne
    @JoinColumn(name = "articulo_inmsumo_id")
    private ArticuloInsumo articuloInsumo;

    @OneToOne
    @JoinColumn(name = "articulo_manufacturado_id")
    private ArticuloManufacturado articuloManufacturado;

    @ManyToOne
    @JoinColumn(name = "promoción_id")
    private Promocion promocion;
}

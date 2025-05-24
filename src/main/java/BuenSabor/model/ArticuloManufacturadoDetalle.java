package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ArticuloManufacturadoDetalle extends EntityApp {

    private double cantidad;

    @ManyToOne
    @JoinColumn(name = "articulo_insumo_id")
    private ArticuloInsumo insumo;

    @ManyToOne
    @JoinColumn(name = "articulo_manufacturado_id")
    @JsonManagedReference
    private ArticuloManufacturado manufacturado;

}

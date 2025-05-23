package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ArticuloManufacturado extends EntityApp {

    private String denominacion;
    private String descripcion;
    private double precioVenta;
    private Integer tiempoEstimado;
    //El precio de costo se calcula en funcion del costo de los materiales
    @Transient
    private double precioCosto;

    @ManyToOne
    @JoinColumn(name = "categoria_articulo_manufacturado_id")
    private CategoriaArticuloManufacturado categoria;

    //la lista de ingredientes que componen al articulo manufacturado/ plato
    @OneToMany(mappedBy = "manufacturado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticuloManufacturadoDetalle> detalles;

}

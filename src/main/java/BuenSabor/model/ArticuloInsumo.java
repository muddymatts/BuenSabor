package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ArticuloInsumo extends EntityApp {

    private String denominacion;
    private double precioCompra;
    private double precioVenta;
    private boolean esParaElaborar;

    // Relación N-1 con CategoriaArticulo
    @ManyToOne
    @JoinColumn(name = "categoria_articulo_insumo_id", nullable = false)
    private CategoriaArticulo categoriaArticulo;

    // Relación 1-1 con ImagenInsumo
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "imagen_insumo_id")
    private ImagenInsumo imagenInsumo;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id", nullable = false)
    private UnidadMedida unidadMedida;

}

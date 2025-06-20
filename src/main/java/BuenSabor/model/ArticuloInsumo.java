package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ArticuloInsumo extends EntityApp {

    private String denominacion;
    private double precioCompra;
    private double precioVenta;
    private boolean esParaElaborar;

    // Relación N-1 con CategoriaArticulo
    @ManyToOne
    @JoinColumn(name = "categoria_articulo_insumo_id")
    private CategoriaArticulo categoriaArticulo;

    // Relación 1-1 con ImagenInsumo
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "imagen_insumo_id")
    private ImagenInsumo imagenInsumo;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id")
    private UnidadMedida unidadMedida;

    @OneToMany(mappedBy = "insumo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ArticuloManufacturadoDetalle> detalles;

}

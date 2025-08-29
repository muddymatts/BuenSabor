package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ArticuloManufacturado extends EntityApp {

    private String denominacion;
    private String descripcion;
    private double precioVenta;
    private Integer tiempoEstimado;
    private double precioCosto = 0.0;

    @Transient
    public double getPrecioCosto() {
        // Lógica de cálculo (ejemplo: suma de costos de detalles)
        if (detalles != null) {
            return detalles.stream()
                    .mapToDouble(detalle -> detalle.getInsumo().getPrecioCompra() * detalle.getCantidad())
                    .sum();
        }
        return 0.0;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_articulo_manufacturado_id")
    private CategoriaArticuloManufacturado categoria;

    //la lista de ingredientes que componen al articulo manufacturado/ plato
    @OneToMany(mappedBy = "manufacturado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticuloManufacturadoDetalle> detalles;

    @OneToMany(mappedBy = "articuloManufacturado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagenManufacturado> imagenes;

    @JsonProperty("categoria")
    public String getCategoriaId() {
        return (categoria != null) ? categoria.getDenominacion() : null;
    }
}

package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CategoriaArticulo extends EntityApp {

    private String denominacion;

    @OneToMany(mappedBy = "categoriaArticulo", cascade = CascadeType.ALL)
    private List<ArticuloInsumo> articulos;

    @ManyToOne
    @JoinColumn(name = "categoria_padre_id", nullable = true)
    private CategoriaArticulo categoriaPadre;

}


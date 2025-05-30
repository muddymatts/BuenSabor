package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CategoriaArticulo extends EntityApp {
//categorias para los articulos manufacturados: platos

    private String denominacion;
    /*
        @OneToMany(mappedBy = "categoriaArticulo", cascade = CascadeType.ALL)
        private List<ArticuloInsumo> articulos;
    */
    @ManyToOne
    @JoinColumn(name = "categoria_padre_id", nullable = true)
    private CategoriaArticulo categoriaPadre;

}


package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CategoriaArticuloInsumo extends EntityApp {

    private String denominacion;

    @ManyToOne
    @JoinColumn(name = "categoria_padre_id")
    private CategoriaArticuloInsumo categoriaPadre;

    private List<String> recorrerCategorias (CategoriaArticuloInsumo categoria, List<String> categoriasAnidadas){

        categoriasAnidadas.add(categoria.getDenominacion());

        if (categoria.categoriaPadre == null) return categoriasAnidadas;

        recorrerCategorias(categoria.getCategoriaPadre(), categoriasAnidadas);

        return categoriasAnidadas;
    }

    @JsonIgnore
    public List<String> getCategoriasAnidadas(){
        return recorrerCategorias(this, new java.util.ArrayList<>());
    }

}
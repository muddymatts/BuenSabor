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
public class CategoriaArticuloInsumo extends EntityApp {

    private String denominacion;

    @ManyToOne
    @JoinColumn(name = "categoria_padre_id")
    @JsonBackReference
    private CategoriaArticuloInsumo categoriaPadre;

}
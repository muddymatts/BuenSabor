package BuenSabor.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UnidadMedida extends EntityBean {

    private String denominacion;

    /*
    @OneToMany(mappedBy = "unidadMedida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticuloInsumo> insumos;
    */

}

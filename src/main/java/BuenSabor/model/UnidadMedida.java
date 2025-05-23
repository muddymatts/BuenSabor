package BuenSabor.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

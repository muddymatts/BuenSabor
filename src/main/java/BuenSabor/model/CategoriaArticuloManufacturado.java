package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CategoriaArticuloManufacturado extends EntityApp {

    private String denominacion;

    @OneToMany(mappedBy ="categoria")
    private List<ArticuloManufacturado> articulos;

}

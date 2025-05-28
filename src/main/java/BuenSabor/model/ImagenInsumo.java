package BuenSabor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class ImagenInsumo extends EntityApp {

    private String descripcion;

    @OneToOne(mappedBy = "imagenInsumo")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private ArticuloInsumo articulo;
}

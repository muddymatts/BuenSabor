package BuenSabor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class ImagenInsumo extends EntityApp {

    private String descripcion;

    @OneToOne(mappedBy = "imagenInsumo")
    private ArticuloInsumo articulo;
}

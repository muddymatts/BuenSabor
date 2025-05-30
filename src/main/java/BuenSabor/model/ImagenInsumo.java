package BuenSabor.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ImagenInsumo extends EntityApp {

    private String descripcion;

}

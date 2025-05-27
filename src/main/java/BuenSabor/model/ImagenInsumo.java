package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class ImagenInsumo extends EntityApp {

    private String descripcion;

}

package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pais {

    @Id
    private int Id;
    private String nombre;
}

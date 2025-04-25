package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Provincia {

    @Id
    private int Id;
    private String nombre;

    // Relación N-1 con País
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;
}

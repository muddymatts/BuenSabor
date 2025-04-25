package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String nombre;

    // Relación N-1 con Provincia
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

}

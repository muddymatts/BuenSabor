package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String calle;

    @ColumnDefault("0")
    private int numero;

    private int cp;

    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
}

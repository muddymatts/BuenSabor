package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
public class Domicilio {

    @Id
    private int id;

    private String calle;

    @ColumnDefault("0")
    private int numero;

    private int cp;

    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
}

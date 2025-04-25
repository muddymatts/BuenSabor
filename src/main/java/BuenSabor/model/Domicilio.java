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
public class Domicilio {

    @Id
    private int id;


    private String calle;
    private int numero;
    private int cp;

    // Relación N-1 con Localidad
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
}

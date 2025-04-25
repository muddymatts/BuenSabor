package BuenSabor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Promocion extends EntityApp {

    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private double descuento;

    @OneToMany(mappedBy = "promocion")
    private List<PromocionDetalle> detalle;

}

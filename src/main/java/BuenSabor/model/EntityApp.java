package BuenSabor.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class EntityApp extends EntityBean {

    private LocalDate fechaAlta = LocalDate.now();
    private LocalDate fechaBaja;

    public void setFechaBaja() {
        this.fechaBaja = LocalDate.now();
    }

    public void eliminarBaja(){
        this.fechaBaja = null;
    }

    public boolean estaActivo() {
        return this.fechaBaja == null;
    }

}

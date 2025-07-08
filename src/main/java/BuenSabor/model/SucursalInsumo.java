package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SucursalInsumo extends EntityBean {

    private Long stockActual;
    private Long stockMinimo;
    private Long stockMaximo;

    // Relación N-1 con SucursalEmpresa
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEmpresa sucursal;

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private ArticuloInsumo insumo;
}

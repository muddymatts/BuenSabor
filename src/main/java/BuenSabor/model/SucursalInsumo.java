package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"sucursal_id", "insumo_id"}))
public class SucursalInsumo extends EntityBean {

    private Long stockActual;
    private Long stockMinimo;
    private Long stockMaximo;

    // Relación N-1 con SucursalEmpresa
    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private SucursalEmpresa sucursal;

    @ManyToOne
    @JoinColumn(name = "insumo_id", nullable = false)
    private ArticuloInsumo insumo;
}

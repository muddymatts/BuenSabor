package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SucursalInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String stockActual;
    private String stockMinimo;
    private String stockMaximo;

    // Relación N-1 con SucursalEmpresa
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEmpresa sucursal;

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private ArticuloInsumo insumo;
}

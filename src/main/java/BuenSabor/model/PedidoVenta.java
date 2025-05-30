package BuenSabor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class PedidoVenta extends EntityApp {

    private String horaEstimadaFinalizacion;
    private BigDecimal subtotal;
    private BigDecimal gastosEnvio;
    private BigDecimal total;
    private BigDecimal toalCosto;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private TipoEnvio tipoEnvio;

    private LocalDate fechaPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoVentaDetalle> detalles;

    @ManyToOne
    @JoinColumn(name = "factura_venta_id")
    private FacturaVenta facturaVenta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "sucursal_empresa_id")
    private SucursalEmpresa sucursalEmpresa;

}

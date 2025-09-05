package BuenSabor.model;

import BuenSabor.enums.FormaPago;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class FacturaVenta extends EntityApp {

    private Date fechaFacturacion;
    private String numeroComprobante;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal gastosEnvio;
    private BigDecimal totalVenta;

    @OneToMany(mappedBy = "facturaVenta")
    private List<PedidoVenta> pedidos;

    @OneToMany(mappedBy = "facturaVenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FacturaVentaDetalle> detalles;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;


}

package BuenSabor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class DatosMercadoPago extends EntityApp {

    @Column(name = "payment_id", unique = true)
    private Long paymentId;


    private Date date_created;
    private Date date_approved;
    private Date date_last_update;
    private String payment_type_id;
    private String payment_method_id;
    private String status;
    private String status_detail;

    @OneToOne
    @JoinColumn(name = "factura_venta_id")
    private FacturaVenta facturaVenta;

    @OneToOne
    @JoinColumn(name = "pedido_venta_id")
    private PedidoVenta pedidoVenta;
}

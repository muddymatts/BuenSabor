package BuenSabor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DatosMercadoPago extends EntityApp {

    @Column(name = "payment_id", unique = true)
    private Long paymentId;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_approved")
    private LocalDateTime dateApproved;

    @Column(name = "date_last_update")
    private LocalDateTime dateLastUpdate;

    private String paymentTypeId;
    private String paymentMethodId;
    private String status;
    private String statusDetail;

    @OneToOne
    @JoinColumn(name = "factura_venta_id")
    private FacturaVenta facturaVenta;

    @OneToOne
    @JoinColumn(name = "pedido_venta_id")
    private PedidoVenta pedidoVenta;
}

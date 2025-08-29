package BuenSabor.dto.mercadoPago.paymentResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {

    private Long id;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_approved")
    private String dateApproved;

    @JsonProperty("date_last_updated")
    private String dateLastUpdated;

    @JsonProperty("payment_type_id")
    private String paymentTypeId;

    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("factura_venta_id")
    private String externalReference;
}


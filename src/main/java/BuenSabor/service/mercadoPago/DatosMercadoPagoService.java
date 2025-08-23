package BuenSabor.service.mercadoPago;

import BuenSabor.dto.mercadoPago.paymentResponse.PaymentResponseDTO;
import BuenSabor.model.DatosMercadoPago;
import BuenSabor.repository.DatosMercadoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class DatosMercadoPagoService {

    private final DatosMercadoPagoRepository repository;

    @Transactional
    public void guardarDesdeRespuesta(PaymentResponseDTO dto) {
        Long paymentId = dto.getId();

        DatosMercadoPago datos = paymentId != null
                ? repository.findByPaymentId(paymentId).orElseGet(DatosMercadoPago::new)
                : new DatosMercadoPago();

        datos.setPaymentId(paymentId);
        datos.setDate_created(parseDate(dto.getDateCreated()));
        datos.setDate_approved(parseDate(dto.getDateApproved()));
        datos.setDate_last_update(parseDate(dto.getDateLastUpdated()));
        datos.setPayment_type_id(dto.getPaymentTypeId());
        datos.setPayment_method_id(dto.getPaymentMethodId());
        datos.setStatus(dto.getStatus());
        datos.setStatus_detail(dto.getStatusDetail());

        try {
            repository.save(datos);
        } catch (DataIntegrityViolationException e) {
            if (paymentId != null) {
                repository.findByPaymentId(paymentId).orElseThrow(() -> e);
                return;
            }
            throw e;
        }
    }

    private Date parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return Date.from(OffsetDateTime.parse(value).toInstant());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}


package BuenSabor.service.mercadoPago;

import BuenSabor.dto.mercadoPago.paymentResponse.PaymentResponseDTO;
import BuenSabor.model.DatosMercadoPago;
import BuenSabor.repository.DatosMercadoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;


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
        datos.setDateCreated(parseToLocalDateTime(dto.getDateCreated()));
        datos.setDateApproved(parseToLocalDateTime(dto.getDateApproved()));
        datos.setDateLastUpdate(parseToLocalDateTime(dto.getDateLastUpdated()));
        datos.setPaymentTypeId(dto.getPaymentTypeId());
        datos.setPaymentMethodId(dto.getPaymentMethodId());
        datos.setStatus(dto.getStatus());
        datos.setStatusDetail(dto.getStatusDetail());

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

    private LocalDateTime parseToLocalDateTime(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            OffsetDateTime odt = OffsetDateTime.parse(value);
            return odt.toInstant()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDateTime();
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}


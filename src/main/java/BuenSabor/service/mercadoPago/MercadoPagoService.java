package BuenSabor.service.mercadoPago;

import BuenSabor.config.LocalTunnelManager;
import BuenSabor.dto.mercadoPago.paymentResponse.PaymentResponseDTO;
import BuenSabor.dto.mercadoPago.preferenceMp.PreferenceIdDTO;
import BuenSabor.dto.mercadoPago.preferenceMp.PreferenceItemDTO;
import BuenSabor.dto.mercadoPago.preferenceMp.PreferencePedidoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MercadoPagoService {

    private final ObjectMapper mapper = new ObjectMapper();
    private final LocalTunnelManager localTunnelManager;
    private static final Logger logger = Logger.getLogger(MercadoPagoService.class.getName());
    Dotenv dotenv = Dotenv.load();

    public MercadoPagoService(LocalTunnelManager localTunnelManager) {
        this.localTunnelManager = localTunnelManager;
        String accessToken = dotenv.get("PROD_ACCESS_TOKEN");
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public PreferenceIdDTO solicitarIdPreferencia(PreferencePedidoDTO pedidoDto) throws MPException, MPApiException {
        Preference preference;
        String tunnelUrl = localTunnelManager.getLocalTunnelUrl();
        String tunnelUrlCompleta = tunnelUrl + "/api/mercadopago/webhook";

        try {
            List<PreferenceItemRequest> items = new ArrayList<>();

            // TODO: redireccionar al nuevo componente de estado del pedido una vez creado
            PreferenceBackUrlsRequest backUrls =
                    PreferenceBackUrlsRequest.builder()
                            .success("https://localhost:5173/productos")
                            .pending("https://localhost:5173/productos")
                            .failure("https://localhost:5173/productos")
                            .build();

            for (PreferenceItemDTO dto : pedidoDto.getItems()) {
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(dto.getId())
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .pictureUrl(dto.getPictureUrl())
                        .categoryId(dto.getCategoryId())
                        .quantity(dto.getQuantity())
                        .currencyId(dto.getCurrencyId())
                        .unitPrice(dto.getUnitPrice())
                        .build();
                items.add(itemRequest);
            }

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .notificationUrl(tunnelUrlCompleta)
                    .externalReference(pedidoDto.getIdPedido())
                    .build();

            PreferenceClient client = new PreferenceClient();
            preference = client.create(preferenceRequest);

        } catch (MPException | MPApiException e) {
            logger.log(Level.SEVERE, "Error al crear la preferencia en Mercado Pago", e);
            throw e;
        }

        return toDto(preference);
    }

    public PaymentResponseDTO processWebhook(String body) throws Exception {
        JsonNode root = mapper.readTree(body);
        String paymentIdStr = extractPaymentId(root);

        if (paymentIdStr == null || paymentIdStr.isBlank()) {
            throw new IllegalArgumentException("No se encontró paymentId en el webhook: " + body);
        }

        Long paymentId = Long.parseLong(paymentIdStr);
        Payment payment = getPaymentById(paymentId);

        return new PaymentResponseDTO(
                payment.getId(),
                payment.getDateCreated() != null ? payment.getDateCreated().toString() : null,
                payment.getDateApproved() != null ? payment.getDateApproved().toString() : null,
                payment.getDateLastUpdated() != null ? payment.getDateLastUpdated().toString() : null,
                payment.getPaymentTypeId(),
                payment.getPaymentMethodId(),
                payment.getStatus(),
                payment.getStatusDetail()
        );
    }

    private PreferenceIdDTO toDto(Preference preference) {
        if (preference == null) {
            return null;
        }

        return new PreferenceIdDTO(
                preference.getId(),
                preference.getInitPoint()
        );
    }

    private String extractPaymentId(JsonNode root) {
        if (root.has("data") && root.path("data").has("id")) {
            return root.path("data").path("id").asText();
        }

        if (root.has("resource") && "payment".equals(root.path("topic").asText())) {
            return root.path("resource").asText();
        }

        root.path("topic").asText();
        return null;
    }

    private Payment getPaymentById(Long paymentId) throws Exception {
        PaymentClient paymentClient = new PaymentClient();
        return paymentClient.get(paymentId);
    }
}

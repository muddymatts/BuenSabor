package BuenSabor.service;

import BuenSabor.config.LocalTunnelManager;
import BuenSabor.dto.preferenceMp.PreferenceIdDTO;
import BuenSabor.dto.preferenceMp.PreferenceItemDTO;
import BuenSabor.dto.preferenceMp.PreferencePedidoDTO;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MercadoPagoService {

    private final LocalTunnelManager localTunnelManager;

    private static final Logger logger = Logger.getLogger(MercadoPagoService.class.getName());

    public MercadoPagoService(LocalTunnelManager localTunnelManager) {
        this.localTunnelManager = localTunnelManager;
    }

    public PreferenceIdDTO solicitarIdPreferencia(PreferencePedidoDTO pedidoDto) throws MPException, MPApiException {
        Preference preference;
        String tunnelUrl = localTunnelManager.getLocalTunnelUrl();
        System.out.println(tunnelUrl);

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
                    .notificationUrl(tunnelUrl + "/mercadopago/notification")
                    .build();

            PreferenceClient client = new PreferenceClient();
            preference = client.create(preferenceRequest);

        } catch (MPException | MPApiException e) {
            logger.log(Level.SEVERE, "Error al crear la preferencia en Mercado Pago", e);
            throw e;
        }

        return toDto(preference);
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
}

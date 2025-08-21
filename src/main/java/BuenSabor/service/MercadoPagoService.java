package BuenSabor.service;

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

    private static final Logger logger = Logger.getLogger(MercadoPagoService.class.getName());

    public PreferenceIdDTO solicitarIdPreferencia(PreferencePedidoDTO pedidoDto) throws MPException, MPApiException {
        Preference preference;

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

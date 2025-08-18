package BuenSabor.service;

import BuenSabor.dto.preferenceMp.PreferenceMpDTO;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MercadoPagoService {

    private static final Logger logger = Logger.getLogger(MercadoPagoService.class.getName());

    public PreferenceMpDTO solicitarIdPreferencia() throws MPException, MPApiException {
        Preference preference;

        try {
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id("1234")
                            .title("Games")
                            .description("PS5")
                            .pictureUrl("http://picture.com/PS5")
                            .categoryId("games")
                            .quantity(2)
                            .currencyId("BRL")
                            .unitPrice(new BigDecimal("4000"))
                            .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items).build();

            PreferenceClient client = new PreferenceClient();
            preference = client.create(preferenceRequest);
        } catch (MPException | MPApiException e) {
            logger.log(Level.SEVERE, "Error al crear la preferencia en Mercado Pago", e);
            throw e;
        }

        return new PreferenceMpDTO(preference.getId());
    }
}

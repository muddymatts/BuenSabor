package BuenSabor.controller;

import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.MercadoPagoConfig;

import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MercadoPagoController {

    private final String mpKey;

    public MercadoPagoController() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        this.mpKey = dotenv.get("VITE_MERCADOPAGO_KEY");

        if (mpKey == null || mpKey.isEmpty()) {
            throw new IllegalStateException("Falta la variable de entorno VITE_MERCADOPAGO_KEY.");
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Pedido pedido) {
        try {
            Pedido pedidoGuardado = pedidoService.crearPedido(pedido);

            MercadoPagoConfig.setAccessToken(mpKey);

            PreferenceClient client = new PreferenceClient();



            List<PreferenceItemRequest> items = pedidoGuardado.getDetalles().stream().map(detalle ->
                    PreferenceItemRequest.builder()
                            .title(detalle.getInstrumento().getInstrumento())
                            .quantity(detalle.getCantidad())
                            .unitPrice(BigDecimal.valueOf(detalle.getInstrumento().getPrecio()))
                            .currencyId("ARS")
                            .build()
            ).toList();

            PreferenceRequest request = PreferenceRequest.builder()
                    .items(items)
                    .build();

            Preference preference = client.create(request);

            return ResponseEntity.ok(Map.of(
                    "id", preference.getId(),
                    "init_point", preference.getInitPoint()
            ));

        }catch (MPApiException mpEx) {
            String errorJson = mpEx.getApiResponse().getContent();
            System.out.println("Error Mercado Pago detallado: " + errorJson);
            return ResponseEntity.status(500).body("Error Mercado Pago: " + errorJson);
        } catch (MPException mpEx) {
            System.out.println("Error Mercado Pago (general): " + mpEx.getMessage());
            return ResponseEntity.status(500).body("Error Mercado Pago: " + mpEx.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error general: " + e.getMessage());
        }
    }
}


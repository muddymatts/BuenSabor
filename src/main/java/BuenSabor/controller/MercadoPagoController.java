package BuenSabor.controller;

import BuenSabor.dto.mercadoPago.paymentResponse.PaymentResponseDTO;
import BuenSabor.dto.mercadoPago.preferenceMp.PreferenceIdDTO;
import BuenSabor.dto.mercadoPago.preferenceMp.PreferencePedidoDTO;
import BuenSabor.service.mercadoPago.DatosMercadoPagoService;
import BuenSabor.service.mercadoPago.MercadoPagoService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercadopago")
@CrossOrigin(origins = "*")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;
    private final DatosMercadoPagoService datosMercadoPagoService;

    public MercadoPagoController(MercadoPagoService mercadoPagoService, DatosMercadoPagoService datosMercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
        this.datosMercadoPagoService = datosMercadoPagoService;
    }

    @PostMapping("/checkout")
    public PreferenceIdDTO checkout(@RequestBody PreferencePedidoDTO pedido)
            throws MPException, MPApiException {
        return mercadoPagoService.solicitarIdPreferencia(pedido);
    }

    @PostMapping("/webhook")
    public ResponseEntity<PaymentResponseDTO> webhook(@RequestBody String body) {
        try {
            PaymentResponseDTO dto = mercadoPagoService.processWebhook(body);
            datosMercadoPagoService.guardarDesdeRespuesta(dto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}


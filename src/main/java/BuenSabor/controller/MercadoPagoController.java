package BuenSabor.controller;

import BuenSabor.dto.preferenceMp.PreferenceIdDTO;
import BuenSabor.dto.preferenceMp.PreferencePedidoDTO;
import BuenSabor.service.MercadoPagoService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    public MercadoPagoController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/checkout")
    public PreferenceIdDTO checkout(@RequestBody PreferencePedidoDTO pedido)
            throws MPException, MPApiException {
        return mercadoPagoService.solicitarIdPreferencia(pedido);
    }
}


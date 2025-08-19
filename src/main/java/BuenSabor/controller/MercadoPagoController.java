package BuenSabor.controller;

import BuenSabor.dto.preferenceMp.PreferenceMpDTO;
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
    public PreferenceMpDTO checkout() throws MPException, MPApiException {
        return mercadoPagoService.solicitarIdPreferencia();
    }
}


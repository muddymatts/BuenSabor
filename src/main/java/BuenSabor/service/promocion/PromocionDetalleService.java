package BuenSabor.service.promocion;

import BuenSabor.repository.PromocionDetalleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromocionDetalleService {

    private final PromocionDetalleRepository promocionDetalleRepository;


}

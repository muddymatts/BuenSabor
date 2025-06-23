package BuenSabor.service.promocion;

import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.mapper.PromocionMapper;
import BuenSabor.model.Promocion;
import BuenSabor.repository.PromocionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromocionService {

    private final PromocionRepository promocionRepository;
    private final PromocionMapper promocionMapper;

    public PromocionDTO guardar(Promocion promocion) {
        if (!promocion.getDetalle().isEmpty()){
            Promocion finalPromocion = promocion;
            promocion.getDetalle().forEach(d -> d.setPromocion(finalPromocion));
        }
        promocion = promocionRepository.save(promocion);
        return promocionMapper.toDto(promocion);
    }

    public Promocion crearPromocion(Promocion promocion){

        if (!promocion.getDetalle().isEmpty()){
            promocion.getDetalle().forEach(d -> d.setPromocion(promocion));
        }
        return promocionRepository.save(promocion);
    }

    public PromocionDTO buscarPorId(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
        return promocionMapper.toDto(promocion);
    }

    public Promocion findById(Long id){
        return promocionRepository.findByIdAndFechaBajaIsNull(id);
    }

    public Iterable<Promocion> findAll(){
        return promocionRepository.findAll();
    }
}

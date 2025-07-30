package BuenSabor.service.promocion;

import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.mapper.PromocionMapper;
import BuenSabor.model.Promocion;
import BuenSabor.repository.PromocionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromocionService {

    private final PromocionRepository promocionRepository;
    private final PromocionMapper promocionMapper;

    public PromocionDTO guardar(Promocion promocion) {
        if (!promocion.getDetalle().isEmpty()){
            promocion.getDetalle().forEach(d -> d.setPromocion(promocion));
        } else throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "La promocion debe tener al menos un detalle");

        if (promocion.getDescuento() >= 1 || promocion.getDescuento() <= 0){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "El descuento debe estar entre 0 y 0.99");
        }

        return promocionMapper.toDto(promocionRepository.save(promocion));
    }


    public PromocionDTO buscarPorId(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
        return promocionMapper.toDto(promocion);
    }

    public Promocion findById(Long id){
        return promocionRepository.findByIdAndFechaBajaIsNull(id);
    }

    public Iterable<PromocionDTO> findAll(){
        List<Promocion> listado = promocionRepository.findAll();
        List<PromocionDTO> listadoDTO = new ArrayList<>();
        listado.forEach(promocion -> listadoDTO.add(promocionMapper.toDto(promocion)));
        return listadoDTO;
    }
}

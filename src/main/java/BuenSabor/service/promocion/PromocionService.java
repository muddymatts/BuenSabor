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

    public PromocionDTO guardarPromocion(Promocion promocion) {
        if (promocion.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La promoción nueva no debe tener un ID"
            );
        }

        validarPromocion(promocion);

        return promocionMapper.toDto(promocionRepository.save(promocion));
    }

    public PromocionDTO editarPromocion(Promocion promocion) {
        if (promocion.getId() == null || !promocionRepository.existsById(promocion.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La promoción a editar no existe."
            );
        }

        validarPromocion(promocion);

        Promocion promocionActual = promocionRepository.findById(promocion.getId()).orElseThrow();

        if (promocion.equals(promocionActual)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_MODIFIED,
                    "No se detectaron cambios en la promoción."
            );
        }

        return promocionMapper.toDto(promocionRepository.save(promocion));
    }

    public PromocionDTO buscarPorId(Long id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
        return promocionMapper.toDto(promocion);
    }

    public Promocion findById(Long id) {
        return promocionRepository.findByIdAndFechaBajaIsNull(id);
    }

    public Iterable<PromocionDTO> findAll() {
        List<Promocion> listado = promocionRepository.findAll();
        List<PromocionDTO> listadoDTO = new ArrayList<>();
        listado.forEach(promocion -> listadoDTO.add(promocionMapper.toDto(promocion)));
        return listadoDTO;
    }

    public void eliminarPromocion(Long id) {
        if (!promocionRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La promoción a eliminar no existe."
            );
        }
        promocionRepository.deleteById(id);
    }

    private void validarPromocion(Promocion promocion) {
        if (promocion.getDetalle().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "La promoción debe tener al menos un detalle"
            );
        }
        promocion.getDetalle().forEach(d -> d.setPromocion(promocion));

        if (promocion.getDescuento() >= 1 || promocion.getDescuento() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "El descuento debe estar entre 0 y 0.99"
            );
        }
    }
}

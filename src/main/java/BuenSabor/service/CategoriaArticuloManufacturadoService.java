package BuenSabor.service;

import BuenSabor.dto.CategoriaArticuloManufacturadoDTO;
import BuenSabor.model.CategoriaArticuloManufacturado;
import BuenSabor.repository.CategoriaArticuloManufacturadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaArticuloManufacturadoService {

    private final CategoriaArticuloManufacturadoRepository repository;

    public CategoriaArticuloManufacturadoService(CategoriaArticuloManufacturadoRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaArticuloManufacturadoDTO> obtenerTodas() {
        return repository.findByFechaBajaIsNull().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaArticuloManufacturado crear(CategoriaArticuloManufacturado cat) {
        return repository.save(cat);
    }

    private CategoriaArticuloManufacturadoDTO mapToDTO(CategoriaArticuloManufacturado categoria) {
        CategoriaArticuloManufacturadoDTO dto = new CategoriaArticuloManufacturadoDTO();
        dto.setId(categoria.getId());
        dto.setDenominacion(categoria.getDenominacion());
        return dto;
    }
}
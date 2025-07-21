package BuenSabor.service.articuloInsumo;

import BuenSabor.dto.articuloInsumo.ArticuloInsumoDTO;
import BuenSabor.mapper.ArticuloInsumoMapper;
import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.CategoriaArticuloInsumo;
import BuenSabor.repository.ArticuloInsumoRepository;
import BuenSabor.service.BajaLogicaService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ArticuloInsumoService extends BajaLogicaService {

    private final ArticuloInsumoRepository repository;
    private final ArticuloInsumoMapper mapperInsumo;

    public ArticuloInsumoService(
            ArticuloInsumoRepository repository,
            ArticuloInsumoMapper mapperInsumo) {
        this.repository = repository;
        this.mapperInsumo = mapperInsumo;
    }

    @Transactional
    public ArticuloInsumo crear(ArticuloInsumo insumo) {
        if(
                insumo.getImagenInsumo() == null ||
                insumo.getImagenInsumo().getDenominacion() == null ||
                insumo.getImagenInsumo().getDenominacion().isBlank()
        ){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "debe cargar una imagen para el insumo: " + insumo.getDenominacion());
        }
        return repository.save(insumo);
    }

    public ArticuloInsumo findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ArticuloInsumo> listarTodas() { return repository.findAll(); }

    @Transactional
    public ArticuloInsumo bajaLogica(Long id) {
        darDeBaja(ArticuloInsumo.class, id);
        return repository.findById(id).orElse(null);
    }

    public ArticuloInsumo editarInsumo(ArticuloInsumo insumo) {
        return repository.save(insumo);
    }

    @Transactional
    public ArticuloInsumo anularBaja (Long id){
        reestablecer(ArticuloInsumo.class, id);
        return repository.findById(id).orElse(null);
    }

    public List<String> getCategogoriasAnidadas (CategoriaArticuloInsumo categoria, List<String> categoriasAnidadas){
        if (categoria == null) return categoriasAnidadas;

        getCategogoriasAnidadas(categoria.getCategoriaPadre(), categoriasAnidadas);

        categoriasAnidadas.add(categoria.getDenominacion());

        return categoriasAnidadas;
    }

    public List<ArticuloInsumoDTO> getInsumosDTO() {
        List<ArticuloInsumo> insumos = repository.findAll();
        return insumos.stream()
                .map(insumo -> {
                    ArticuloInsumoDTO insumoDTO =  mapperInsumo.toDto(insumo);
                    if (insumo.getImagenInsumo() != null) {
                        insumoDTO.setNombreImagen(insumo.getImagenInsumo().getDenominacion());
                    } else {
                        insumoDTO.setNombreImagen("no image");
                    }
                    insumoDTO.setCategorias(getCategogoriasAnidadas(insumo.getCategoriaArticuloInsumo(), insumoDTO.getCategorias()));
                    insumoDTO.setNombreUnidadMedida(insumo.getUnidadMedida().getDenominacion());
                    return insumoDTO;
                })
                .toList();
    }
}
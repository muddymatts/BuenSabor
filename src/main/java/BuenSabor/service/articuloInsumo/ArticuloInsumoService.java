package BuenSabor.service.articuloInsumo;

import BuenSabor.model.ArticuloInsumo;
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

    public ArticuloInsumoService(
            ArticuloInsumoRepository repository) {
        this.repository = repository;
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
}
package BuenSabor.service;

import BuenSabor.model.CategoriaArticuloInsumo;
import BuenSabor.repository.CategoriaArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaArticuloService {

    @Autowired
    private CategoriaArticuloRepository categoriaArticuloRepository;

    @Transactional
    public CategoriaArticuloInsumo crear(CategoriaArticuloInsumo categoriaArticuloInsumo) {
        if (categoriaArticuloInsumo.getCategoriaPadre() != null && categoriaArticuloInsumo.getCategoriaPadre().getId() != null) {
            CategoriaArticuloInsumo padre = categoriaArticuloRepository.findById(
                    categoriaArticuloInsumo.getCategoriaPadre().getId()
            ).orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));
            categoriaArticuloInsumo.setCategoriaPadre(padre);
        }
        return categoriaArticuloRepository.save(categoriaArticuloInsumo);
    }

    @Query()
    public List<CategoriaArticuloInsumo> obtenerTodas() {
        return categoriaArticuloRepository.findAll();
    }

    public CategoriaArticuloInsumo obtenerPorId(Long id) {
        return categoriaArticuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Transactional
    public CategoriaArticuloInsumo actualizar(Long id, CategoriaArticuloInsumo nuevaData) {
        CategoriaArticuloInsumo existente = obtenerPorId(id);
        existente.setDenominacion(nuevaData.getDenominacion());

        if (nuevaData.getCategoriaPadre() != null && nuevaData.getCategoriaPadre().getId() != null) {
            CategoriaArticuloInsumo padre = categoriaArticuloRepository.findById(
                    nuevaData.getCategoriaPadre().getId()
            ).orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));
            existente.setCategoriaPadre(padre);
        } else {
            existente.setCategoriaPadre(null);
        }

        return categoriaArticuloRepository.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        CategoriaArticuloInsumo categoria = obtenerPorId(id);
        categoriaArticuloRepository.delete(categoria);
    }
}

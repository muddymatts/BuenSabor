package BuenSabor.service;

import BuenSabor.model.CategoriaArticulo;
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
    public CategoriaArticulo crear(CategoriaArticulo categoriaArticulo) {
        if (categoriaArticulo.getCategoriaPadre() != null && categoriaArticulo.getCategoriaPadre().getId() != null) {
            CategoriaArticulo padre = categoriaArticuloRepository.findById(
                    categoriaArticulo.getCategoriaPadre().getId()
            ).orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));
            categoriaArticulo.setCategoriaPadre(padre);
        }
        return categoriaArticuloRepository.save(categoriaArticulo);
    }

    @Query()
    public List<CategoriaArticulo> obtenerTodas() {
        return categoriaArticuloRepository.findAll();
    }

    public CategoriaArticulo obtenerPorId(Long id) {
        return categoriaArticuloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    @Transactional
    public CategoriaArticulo actualizar(Long id, CategoriaArticulo nuevaData) {
        CategoriaArticulo existente = obtenerPorId(id);
        existente.setDenominacion(nuevaData.getDenominacion());

        if (nuevaData.getCategoriaPadre() != null && nuevaData.getCategoriaPadre().getId() != null) {
            CategoriaArticulo padre = categoriaArticuloRepository.findById(
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
        CategoriaArticulo categoria = obtenerPorId(id);
        categoriaArticuloRepository.delete(categoria);
    }
}

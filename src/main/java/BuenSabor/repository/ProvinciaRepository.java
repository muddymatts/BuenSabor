package BuenSabor.repository;

import BuenSabor.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
    List<Provincia> findByPaisId(int paisId);

    Optional<Provincia> findByNombreAndPaisId(String nombre, int paisId);
}
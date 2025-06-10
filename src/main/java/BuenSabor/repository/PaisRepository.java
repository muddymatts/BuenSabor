package BuenSabor.repository;

import BuenSabor.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
    Optional<Pais> findById(int id);
    Optional<Pais> findByNombre(String nombre);
}

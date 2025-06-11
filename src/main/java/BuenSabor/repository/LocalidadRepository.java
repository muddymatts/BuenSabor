package BuenSabor.repository;

import BuenSabor.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {
    List<Localidad> findByProvinciaId(int provinciaId);
}

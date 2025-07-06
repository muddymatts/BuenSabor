package BuenSabor.repository;

import BuenSabor.model.CategoriaArticuloInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaArticuloRepository extends JpaRepository<CategoriaArticuloInsumo, Long> {
}
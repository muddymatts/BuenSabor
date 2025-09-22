package BuenSabor.repository;

import BuenSabor.model.ArticuloInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloInsumoRepository extends JpaRepository<ArticuloInsumo, Long> {
}
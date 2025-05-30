package BuenSabor.repository;

import BuenSabor.model.ArticuloInsumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloInsumoRepository extends JpaRepository<ArticuloInsumo, Long> {
    ArticuloInsumo findByIdAndFechaBajaIsNull(Long id);
}
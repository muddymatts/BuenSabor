package BuenSabor.repository;

import BuenSabor.model.PromocionDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionDetalleRepository extends JpaRepository<PromocionDetalle, Long> {
    PromocionDetalle findByIdAndFechaBajaIsNull(Long id);
}

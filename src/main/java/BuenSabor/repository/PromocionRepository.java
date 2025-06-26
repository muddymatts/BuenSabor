package BuenSabor.repository;

import BuenSabor.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
        Promocion findByIdAndFechaBajaIsNull(Long id);
}

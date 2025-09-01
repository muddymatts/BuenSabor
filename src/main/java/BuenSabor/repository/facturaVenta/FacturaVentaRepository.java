package BuenSabor.repository.facturaVenta;

import BuenSabor.model.FacturaVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaVentaRepository extends JpaRepository <FacturaVenta, Long> {
}

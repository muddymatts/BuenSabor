package BuenSabor.repository.facturaVenta;

import BuenSabor.model.FacturaVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FacturaVentaRepository extends JpaRepository <FacturaVenta, Long> {
    List<FacturaVenta> findAllByFechaFacturacionBetween(LocalDate fechaDesde, LocalDate fechaHasta);
}

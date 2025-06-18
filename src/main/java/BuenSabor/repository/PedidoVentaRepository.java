package BuenSabor.repository;

import BuenSabor.model.PedidoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoVentaRepository extends JpaRepository<PedidoVenta, Long> {
}

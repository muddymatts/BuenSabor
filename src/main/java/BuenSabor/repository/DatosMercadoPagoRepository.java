package BuenSabor.repository;

import BuenSabor.model.DatosMercadoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatosMercadoPagoRepository extends JpaRepository<DatosMercadoPago, Long> {
    Optional<DatosMercadoPago> findByPaymentId(Long paymentId);
}
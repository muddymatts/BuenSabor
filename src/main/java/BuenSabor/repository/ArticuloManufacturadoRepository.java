package BuenSabor.repository;

import BuenSabor.model.ArticuloManufacturado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloManufacturadoRepository extends JpaRepository<ArticuloManufacturado, Long> {
    List<ArticuloManufacturado> findByFechaBajaIsNull();

}

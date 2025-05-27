package BuenSabor.repository;

import BuenSabor.model.ArticuloManufacturado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticuloManufacturadoRepository extends JpaRepository<ArticuloManufacturado, Long> {
    List<ArticuloManufacturado> findByFechaBajaIsNull();

    ArticuloManufacturado getReferenceByIdAndFechaBajaIsNull(Long id);
}

package BuenSabor.repository;

import BuenSabor.model.CategoriaArticuloManufacturado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaArticuloManufacturadoRepository extends JpaRepository<CategoriaArticuloManufacturado, Long> {
    List<CategoriaArticuloManufacturado> findByFechaBajaIsNull();
}


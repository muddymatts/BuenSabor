package BuenSabor.repository;

import BuenSabor.model.CategoriaArticuloManufacturado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaArticuloManufacturadoRepository extends JpaRepository<CategoriaArticuloManufacturado, Long> {
    List<CategoriaArticuloManufacturado> findByFechaBajaIsNull();
}


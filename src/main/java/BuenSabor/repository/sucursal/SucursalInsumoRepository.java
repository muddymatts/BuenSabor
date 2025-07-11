package BuenSabor.repository.sucursal;

import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.model.SucursalInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SucursalInsumoRepository extends JpaRepository<SucursalInsumo, Long> {
    Optional<SucursalInsumo> findBySucursalAndInsumo(SucursalEmpresa sucursal, ArticuloInsumo insumo);
}

package BuenSabor.repository.sucursal;

import BuenSabor.model.Empresa;
import BuenSabor.model.SucursalEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalEmpresaRepository extends JpaRepository<SucursalEmpresa, Long>, SucursalEmpresaRepositoryCustom {
    Iterable<SucursalEmpresa> findSucursalEmpresaByEmpresa(Empresa referenceById);
}

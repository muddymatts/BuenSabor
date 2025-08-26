package BuenSabor.repository.sucursal;

import BuenSabor.model.Empresa;
import BuenSabor.model.Provincia;
import BuenSabor.model.SucursalEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SucursalEmpresaRepository extends JpaRepository<SucursalEmpresa, Long>, SucursalEmpresaRepositoryCustom {
    Iterable<SucursalEmpresa> findSucursalEmpresaByEmpresa(Empresa referenceById);

    List<SucursalEmpresa> findByDomicilio_Localidad_Provincia(Provincia provincia);
}

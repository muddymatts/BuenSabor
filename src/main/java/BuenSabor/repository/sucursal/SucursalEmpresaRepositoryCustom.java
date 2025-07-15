package BuenSabor.repository.sucursal;

import BuenSabor.dto.sucursal.CantidadDisponibleDTO;
import BuenSabor.dto.sucursal.SucursalInsumoDTO;

import java.util.List;

public interface SucursalEmpresaRepositoryCustom {
    List<SucursalInsumoDTO> getStock(Long SucursalId);

    List<CantidadDisponibleDTO> getCantidadDisponible (Long SucursalId);
}

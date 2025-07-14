package BuenSabor.repository.sucursal;

import BuenSabor.dto.articuloManufacturado.ArticulosManufacturadosDisponiblesDTO;
import BuenSabor.dto.sucursal.SucursalInsumoDTO;

import java.util.List;

public interface SucursalEmpresaRepositoryCustom {
    List<SucursalInsumoDTO> getStock(Long SucursalId);

    List<ArticulosManufacturadosDisponiblesDTO> getProducts(Long SucursalId);
}

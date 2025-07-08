package BuenSabor.repository.sucursal;

import BuenSabor.dto.sucursal.StockDTO;

import java.util.List;

public interface SucursalEmpresaRepositoryCustom {
    List<StockDTO> getStock(Long SucursalId);
}

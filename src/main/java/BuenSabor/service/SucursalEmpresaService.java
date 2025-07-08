package BuenSabor.service;

import BuenSabor.dto.sucursal.StockDTO;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.repository.sucursal.SucursalEmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalEmpresaService {

    private final SucursalEmpresaRepository repository;

    public SucursalEmpresaService (SucursalEmpresaRepository repository){
        this.repository = repository;
    }

    public SucursalEmpresa guardar(SucursalEmpresa sucursal) {
        return repository.save(sucursal);
    }

    public SucursalEmpresa getSucursal(Long id) { return repository.getReferenceById(id); }

    public List<StockDTO> getStock(Long id) { return repository.getStock(id);
    }
}

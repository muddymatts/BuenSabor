package BuenSabor.service;

import BuenSabor.model.SucursalEmpresa;
import BuenSabor.repository.SucursalEmpresaRepository;
import org.springframework.stereotype.Service;

@Service
public class SucursalEmpresaService {

    private final SucursalEmpresaRepository repository;

    public SucursalEmpresaService (SucursalEmpresaRepository repository){
        this.repository = repository;
    }

    public SucursalEmpresa guardar(SucursalEmpresa sucursal) {
        return repository.save(sucursal);
    }
}

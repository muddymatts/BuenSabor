package BuenSabor.service.empresa;

import BuenSabor.model.Empresa;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.repository.empresa.EmpresaRespository;
import BuenSabor.repository.sucursal.SucursalEmpresaRepository;
import BuenSabor.service.BajaLogicaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService extends BajaLogicaService {

    private final EmpresaRespository respository;
    private final SucursalEmpresaRepository sucursalEmpresaRepository;

    public EmpresaService (EmpresaRespository repository, SucursalEmpresaRepository sucursalEmpresaRepository){
        this.respository = repository;
        this.sucursalEmpresaRepository = sucursalEmpresaRepository;
    }

    public Empresa crear(Empresa empresa) {
        return respository.save(empresa);
    }

    public Empresa getEmpresa(Long id) {
        return respository.getReferenceById(id);
    }

    public Empresa editarEmpresa(Empresa empresa) {
        return respository.save(empresa);
    }

    public Iterable<SucursalEmpresa> getSucursales(Long id) {
        return sucursalEmpresaRepository.findSucursalEmpresaByEmpresa(respository.getReferenceById(id));
    }

    public ResponseEntity<Iterable<Empresa>> findAll() {
        return ResponseEntity.ok(respository.findAll());
    }
}

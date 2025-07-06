package BuenSabor.service.empresa;

import BuenSabor.model.Empresa;
import BuenSabor.repository.empresa.EmpresaRespository;
import BuenSabor.service.BajaLogicaService;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService extends BajaLogicaService {

    private final EmpresaRespository respository;

    public EmpresaService (EmpresaRespository repository){
        this.respository = repository;
    }

    public Empresa crear(Empresa empresa) {
        return respository.save(empresa);
    }

    public Empresa getEmpresa(Long id) {
        return respository.getReferenceById(id);
    }
}

package BuenSabor.controller;

import BuenSabor.model.Empresa;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.service.empresa.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/empresa")
public class EmpresaController {

    private final EmpresaService service;

    public EmpresaController (EmpresaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Empresa> create(@RequestBody Empresa empresa){
        return ResponseEntity.ok(service.crear(empresa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable Long id){
        return ResponseEntity.ok(service.getEmpresa(id));
    }

    @GetMapping
    public ResponseEntity<Iterable<Empresa>> getEmpresas(){
        return service.findAll();
    }

    @GetMapping("/{id}/sucursales")
    public ResponseEntity<Iterable<SucursalEmpresa>> getSucursales(@PathVariable Long id){
        return ResponseEntity.ok(service.getSucursales(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> bajaEmpresa(@PathVariable Long id){
        service.darDeBaja(Empresa.class, id);
        return ResponseEntity.ok(service.getEmpresa(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Empresa> reestablecerEmpresa(@PathVariable Long id){
        service.reestablecer(Empresa.class, id);
        return ResponseEntity.ok(service.getEmpresa(id));
    }

    @PutMapping()
    public ResponseEntity<Empresa> editarEmpresa( @RequestBody Empresa empresa){
        return ResponseEntity.ok(service.editarEmpresa(empresa));
    }
}

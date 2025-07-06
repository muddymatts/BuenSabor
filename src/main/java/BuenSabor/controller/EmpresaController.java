package BuenSabor.controller;

import BuenSabor.model.Empresa;
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
}

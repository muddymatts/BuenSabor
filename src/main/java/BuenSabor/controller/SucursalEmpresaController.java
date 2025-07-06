package BuenSabor.controller;

import BuenSabor.model.SucursalEmpresa;
import BuenSabor.service.SucursalEmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sucursal")
public class SucursalEmpresaController {

    private SucursalEmpresaService service;

    public SucursalEmpresaController (SucursalEmpresaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SucursalEmpresa> crear (@RequestBody SucursalEmpresa sucursal){
        return ResponseEntity.ok(service.guardar(sucursal));
    }

}

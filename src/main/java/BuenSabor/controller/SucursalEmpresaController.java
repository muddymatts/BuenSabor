package BuenSabor.controller;

import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.service.SucursalEmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sucursal")
public class SucursalEmpresaController {

    private final SucursalEmpresaService service;

    public SucursalEmpresaController (SucursalEmpresaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SucursalEmpresa> crear (@RequestBody SucursalEmpresa sucursal){
        return ResponseEntity.ok(service.guardar(sucursal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalEmpresa> getSucursal (@PathVariable Long id){
        return ResponseEntity.ok(service.getSucursal(id));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<List<SucursalInsumoDTO>> verStock (@PathVariable Long id){
       List <SucursalInsumoDTO> stockSucursal = service.getStock(id);
       return ResponseEntity.ok(stockSucursal);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<SucursalInsumoDTO> actualizarStock (@PathVariable Long id, @RequestBody SucursalInsumoDTO stock){
        service.addStock(id, stock);
        return ResponseEntity.ok().build();
    public ResponseEntity<SucursalInsumoDTO> addStock(@PathVariable Long id, @RequestBody SucursalInsumoDTO stock){
        return ResponseEntity.ok(service.addStock(id, stock));
    }
    }

}

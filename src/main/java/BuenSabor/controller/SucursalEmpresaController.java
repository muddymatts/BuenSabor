package BuenSabor.controller;

import BuenSabor.dto.sucursal.StockDTO;
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
    public ResponseEntity<List<StockDTO>> verStock (@PathVariable Long id){
       List <StockDTO> stockSucursal = service.getStock(id);
       return stockSucursal.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(stockSucursal);
    }
    
    public ResponseEntity<StockDTO> actualizarStock (@PathVariable Long id, @RequestBody StockDTO stock){
        //TODO metodo para cargar un insumo al stock
        return null;
    }

}

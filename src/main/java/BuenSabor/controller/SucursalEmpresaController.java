package BuenSabor.controller;

import BuenSabor.dto.articuloManufacturado.ArticulosManufacturadosDisponiblesDTO;
import BuenSabor.dto.promocion.PromocionesDisponiblesDTO;
import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import BuenSabor.dto.sucursal.addInsumoDTO;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.service.sucursal.SucursalEmpresaService;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<SucursalEmpresa>> getSucursales(){
        return ResponseEntity.ok(service.findAll());
    }
    @PostMapping
    public ResponseEntity<SucursalEmpresa> create(@RequestBody SucursalEmpresa sucursal){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(sucursal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalEmpresa> getSucursal (@PathVariable Long id){
        return ResponseEntity.ok(service.getSucursal(id));
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<List<SucursalInsumoDTO>> getStock(@PathVariable Long id){
       List <SucursalInsumoDTO> stockSucursal = service.getStock(id);
       return ResponseEntity.ok(stockSucursal);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<SucursalInsumoDTO> setStock(@PathVariable Long id, @RequestBody SucursalInsumoDTO stock){
        return ResponseEntity.ok(service.setStock(id, stock));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<SucursalInsumoDTO> addStock(@PathVariable Long id, @RequestBody addInsumoDTO dto){
        return ResponseEntity.ok(service.addStock(id, dto.getIdInsumo(), dto.getCantidad()));
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<List<ArticulosManufacturadosDisponiblesDTO>> getProducts(@PathVariable Long id){
        return ResponseEntity.ok(service.getProducts(id));
    }

    @GetMapping("{id}/promociones")
    public ResponseEntity<List<PromocionesDisponiblesDTO>> getPromos(@PathVariable Long id){
        return ResponseEntity.ok(service.getPromos(id));
    }

}

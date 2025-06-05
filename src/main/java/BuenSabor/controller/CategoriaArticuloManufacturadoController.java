package BuenSabor.controller;

import BuenSabor.dto.CategoriaArticuloManufacturadoDTO;
import BuenSabor.model.CategoriaArticuloManufacturado;
import BuenSabor.service.CategoriaArticuloManufacturadoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categoria-articulos-manufacturados")
public class CategoriaArticuloManufacturadoController {

    private final CategoriaArticuloManufacturadoService service;

    public CategoriaArticuloManufacturadoController(CategoriaArticuloManufacturadoService service) {
        this.service = service;
    }

    @PostMapping
    public CategoriaArticuloManufacturado crear(@RequestBody CategoriaArticuloManufacturado categoriaArticuloManufacturado) {
        return service.crear(categoriaArticuloManufacturado);
    }

    @GetMapping
    public List<CategoriaArticuloManufacturadoDTO> obtenerCategorias() {
        return service.obtenerTodas();
    }
}
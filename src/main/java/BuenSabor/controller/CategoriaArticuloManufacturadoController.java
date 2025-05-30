package BuenSabor.controller;

import BuenSabor.model.CategoriaArticuloManufacturado;
import BuenSabor.service.CategoriaArticuloManufacturadoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

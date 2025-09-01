package BuenSabor.controller;

import BuenSabor.dto.pedido.PedidoVentaDTO;
import BuenSabor.dto.response.ResponseDTO;
import BuenSabor.enums.Estado;
import BuenSabor.model.PedidoVenta;
import BuenSabor.service.pedido.PedidoVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoVentaController {

    private final PedidoVentaService pedidoVentaService;

    public PedidoVentaController(PedidoVentaService pedidoVentaService) {
        this.pedidoVentaService = pedidoVentaService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> crear(@RequestBody PedidoVentaDTO pedidoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoVentaService.crear(pedidoDto));
    }

    @GetMapping
    public ResponseEntity<List<?>> listar(@RequestParam(defaultValue = "false") boolean full,
                                          @RequestParam(required = false) Long idCliente,
                                          @RequestParam(required = false) Long idSucursal) {
        if(full){
            List<PedidoVenta> pedidos = pedidoVentaService.listarTodas();
            return ResponseEntity.ok(pedidos);
        } else {
            if(idCliente != null && idSucursal != null){
                return ResponseEntity.ok(pedidoVentaService.getPedidosFiltradosDTO(idCliente,idSucursal));
            } else if(idCliente != null){
                return ResponseEntity.ok(pedidoVentaService.getPedidosFiltradosDTO(idCliente,null));
            } else if(idSucursal != null){
                return ResponseEntity.ok(pedidoVentaService.getPedidosFiltradosDTO(null,idSucursal));
            }
            return ResponseEntity.ok(pedidoVentaService.getPedidosDTO());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> bajaLogica(@PathVariable Long id) {
        pedidoVentaService.darDeBaja(PedidoVenta.class, id);
        return ResponseEntity.ok(new ResponseDTO("Pedido dado de baja.", id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDTO> reestablecer(@PathVariable Long id) {
        pedidoVentaService.reestablecer(PedidoVenta.class, id);
        return ResponseEntity.ok(new ResponseDTO("Pedido reestablecido.", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoVentaDTO> buscarPorId(@PathVariable Long id) {
        PedidoVentaDTO busqueda = pedidoVentaService.buscarPorId(id);

        if (busqueda != null) {
            return ResponseEntity.ok(busqueda);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        } else if (Stream.of(Estado.values())
                .anyMatch(e -> e.name().equals(estado.toLowerCase().trim()))){
            PedidoVenta pedido = pedidoVentaService.actualizarEstado(id, Estado.valueOf(estado.toLowerCase().trim()));
            return ResponseEntity.ok(new ResponseDTO("Pedido actualizado.", pedido.getId()));
        } else {
            throw new IllegalArgumentException("El estado debe ser uno de los siguientes: " + Arrays.toString(Estado.values()) + ".");
        }
    }

}

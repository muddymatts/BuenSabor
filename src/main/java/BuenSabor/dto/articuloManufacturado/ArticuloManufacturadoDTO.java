package BuenSabor.dto.articuloManufacturado;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticuloManufacturadoDTO {
    private Long id;
    private LocalDate fechaBaja;
    private String denominacion;
    private double precioVenta;
    private String nombreCategoria;
    private List<String> listaImagenes = new ArrayList<>();
    private List<String> ingredientes = new ArrayList<>();
}

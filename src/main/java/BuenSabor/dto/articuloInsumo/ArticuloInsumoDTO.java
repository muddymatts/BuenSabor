package BuenSabor.dto.articuloInsumo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ArticuloInsumoDTO {
    private Long id;
    private String denominacion;
    private LocalDate fechaBaja;
    private double precioVenta;
    private double precioCompra;
    private List<String> categorias = new java.util.ArrayList<>();
    private String nombreUnidadMedida;
    private boolean esParaElaborar;
    private String nombreImagen;
}

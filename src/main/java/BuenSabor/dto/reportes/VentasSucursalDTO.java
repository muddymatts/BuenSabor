package BuenSabor.dto.reportes;

import lombok.Data;

@Data
public class VentasSucursalDTO {
    private String fecha;
    private Double total;
    private Integer idPedido;
    private String nombreCliente;
    private Integer idCliente;
    private Integer idFactura;
}

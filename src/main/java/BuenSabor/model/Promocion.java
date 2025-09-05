package BuenSabor.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Promocion extends EntityApp {

    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private double descuento;

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromocionDetalle> detalle = new java.util.ArrayList<>();

    @Transient
    public double getPrecioSinDescuento() {
        if (detalle == null) return 0.0;

        return detalle.stream().mapToDouble(pd -> {
            if (pd.getArticuloInsumo() != null) {
                return pd.getCantidad() * pd.getArticuloInsumo().getPrecioVenta();
            } else if (pd.getArticuloManufacturado() != null) {
                return pd.getCantidad() * pd.getArticuloManufacturado().getPrecioVenta();
            }
            return 0.0;
        }).sum();
    }

    @Transient
    public double getPrecioCosto() {
        if (detalle == null) return 0.0;

        return detalle.stream().mapToDouble(pd -> {
            if (pd.getArticuloInsumo() != null) {
                return pd.getCantidad() * pd.getArticuloInsumo().getPrecioCompra();
            } else if (pd.getArticuloManufacturado() != null) {
                return pd.getCantidad() * pd.getArticuloManufacturado().getPrecioCosto();
            }
            return 0.0;
        }).sum();
    }

    @Transient
    public double getPrecioVenta() {
        return getPrecioSinDescuento() * (1 - descuento);
    }

    @Transient
    public int getDemoraTotal() {
        if (detalle == null) return 0;

        return detalle.stream()
                .filter(pd -> pd.getArticuloManufacturado() != null)
                .mapToInt(pd -> pd.getCantidad() * pd.getArticuloManufacturado().getTiempoEstimado())
                .sum();
    }

}

package BuenSabor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SucursalEmpresa extends EntityApp {


    private String nombre;
    private String horarioApertura;
    private String horarioCierre;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    // Relación 1-1 con Domicilio
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id")
    private Domicilio domicilio;

    // Relación 1-N con SucursalInsumo
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<SucursalInsumo> insumos;
}

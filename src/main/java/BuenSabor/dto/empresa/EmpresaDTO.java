package BuenSabor.dto.empresa;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {

    @JsonProperty("fecha_alta")
    private String fechaAlta;

    @JsonProperty("fecha_baja")
    private String fechaBaja;

    private Long cuil;
    private String nombre;

    @JsonProperty("razon_social")
    private String razonSocial;
}


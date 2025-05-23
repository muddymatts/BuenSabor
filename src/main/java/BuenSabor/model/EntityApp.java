package BuenSabor.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class EntityApp extends EntityBean {

    private Date fechaAlta;
    private Date fechaBaja;

}

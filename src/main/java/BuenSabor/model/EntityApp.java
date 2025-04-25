package BuenSabor.model;

import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public abstract class EntityApp extends EntityBean {

    private Date fechaAlta;
    private Date fechaBaja;
    private boolean estaActivo;


}

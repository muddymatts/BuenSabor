package BuenSabor.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

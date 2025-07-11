package BuenSabor.repository.empresa;

import BuenSabor.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRespository extends JpaRepository<Empresa, Long> {
}

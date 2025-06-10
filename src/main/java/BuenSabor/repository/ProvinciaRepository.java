package BuenSabor.repository;

import BuenSabor.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

   public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
       Optional<Provincia> findByPaisId(int id);
   }
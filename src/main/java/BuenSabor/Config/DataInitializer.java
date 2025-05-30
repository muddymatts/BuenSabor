package BuenSabor.config;

import BuenSabor.model.CategoriaArticulo;
import BuenSabor.repository.CategoriaArticuloRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initCategorias(CategoriaArticuloRepository categoriaArticuloRepository) {
        return args -> {
            if (categoriaArticuloRepository.count() == 0) {
                CategoriaArticulo pizza = new CategoriaArticulo();
                pizza.setDenominacion("Pizza");

                CategoriaArticulo hamburguesa = new CategoriaArticulo();
                hamburguesa.setDenominacion("Hamburguesa");

                CategoriaArticulo sandwich = new CategoriaArticulo();
                sandwich.setDenominacion("Sandwich");

                CategoriaArticulo lomo = new CategoriaArticulo();
                lomo.setDenominacion("Lomo");

                CategoriaArticulo empanadas = new CategoriaArticulo();
                empanadas.setDenominacion("Empanadas");

                categoriaArticuloRepository.save(pizza);
                categoriaArticuloRepository.save(hamburguesa);
                categoriaArticuloRepository.save(sandwich);
                categoriaArticuloRepository.save(lomo);
                categoriaArticuloRepository.save(empanadas);
            }
        };
    }
}
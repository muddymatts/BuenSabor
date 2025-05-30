package BuenSabor.config;

import BuenSabor.model.ArticuloManufacturado;
import BuenSabor.model.CategoriaArticuloManufacturado;
import BuenSabor.repository.ArticuloManufacturadoRepository;
import BuenSabor.repository.CategoriaArticuloManufacturadoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initCategoriasManufacturados(CategoriaArticuloManufacturadoRepository categoriaArticuloManufacturadoRepository) {
        return args -> {
            if (categoriaArticuloManufacturadoRepository.count() == 0) {
                CategoriaArticuloManufacturado pizza = new CategoriaArticuloManufacturado();
                pizza.setDenominacion("Pizza");

                CategoriaArticuloManufacturado hamburguesa = new CategoriaArticuloManufacturado();
                hamburguesa.setDenominacion("Hamburguesa");

                CategoriaArticuloManufacturado sandwich = new CategoriaArticuloManufacturado();
                sandwich.setDenominacion("Sandwich");

                CategoriaArticuloManufacturado lomo = new CategoriaArticuloManufacturado();
                lomo.setDenominacion("Lomo");

                CategoriaArticuloManufacturado empanadas = new CategoriaArticuloManufacturado();
                empanadas.setDenominacion("Empanadas");

                categoriaArticuloManufacturadoRepository.saveAll(List.of(pizza, hamburguesa, sandwich, lomo, empanadas));
            }
        };
    }

    @Bean
    CommandLineRunner initArticuloManufacturado(
            ArticuloManufacturadoRepository articuloManufacturadoRepository,
            CategoriaArticuloManufacturadoRepository categoriaArticuloManufacturadoRepository
    ) {
        return args -> {
            if (articuloManufacturadoRepository.count() == 0) {
                List<CategoriaArticuloManufacturado> categorias = categoriaArticuloManufacturadoRepository.findAll();

                if (!categorias.isEmpty()) {
                    ArticuloManufacturado pizzaMuzzarella = new ArticuloManufacturado();
                    pizzaMuzzarella.setDenominacion("Pizza Muzzarella");
                    pizzaMuzzarella.setDescripcion("Pizza con mozzarella y salsa de tomate");
                    pizzaMuzzarella.setCategoria(categorias.get(0));

                    ArticuloManufacturado hamburguesaClasica = new ArticuloManufacturado();
                    hamburguesaClasica.setDenominacion("Hamburguesa Clásica");
                    hamburguesaClasica.setDescripcion("Hamburguesa con carne vacuna y vegetales frescos");
                    hamburguesaClasica.setCategoria(categorias.get(1));

                    ArticuloManufacturado lomitoSimple = new ArticuloManufacturado();
                    lomitoSimple.setDenominacion("Lomito Simple");
                    lomitoSimple.setDescripcion("Lomito con jamón y queso");
                    lomitoSimple.setCategoria(categorias.get(3));

                    articuloManufacturadoRepository.saveAll(List.of(pizzaMuzzarella, hamburguesaClasica, lomitoSimple));
                }
            }
        };
    }
}
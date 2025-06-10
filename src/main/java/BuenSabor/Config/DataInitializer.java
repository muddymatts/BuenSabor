package BuenSabor.config;

import BuenSabor.enums.Rol;
import BuenSabor.model.*;
import BuenSabor.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner crearUsuarios(UsuarioRepository userRepo, EmpleadoRepository empleadoRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                // Admin
                Empleado adminEmpleado = new Empleado();
                adminEmpleado.setNombre("Admin");
                adminEmpleado.setApellido("Admin");
                adminEmpleado.setTelefono("123456789");
                adminEmpleado.setEmail("admin@example.com");
                adminEmpleado.setRol(Rol.admin);
                empleadoRepo.save(adminEmpleado);

                Usuario adminUser = new Usuario();
                adminUser.setUsername("admin");
                adminUser.setPassword(encoder.encode("admin123"));
                adminUser.setEmpleado(adminEmpleado);
                adminUser.setEstaActivo(true);

                userRepo.save(adminUser);
            }

            if (userRepo.findByUsername("empleado").isEmpty()) {
                // Empleado
                Empleado empleadoEmpleado = new Empleado();
                empleadoEmpleado.setNombre("Empleado");
                empleadoEmpleado.setApellido("Empleado");
                empleadoEmpleado.setTelefono("987654321");
                empleadoEmpleado.setEmail("empleado@example.com");
                empleadoEmpleado.setRol(Rol.empleado);
                empleadoRepo.save(empleadoEmpleado);

                Usuario empleadoUser = new Usuario();
                empleadoUser.setUsername("empleado");
                empleadoUser.setPassword(encoder.encode("empleado123"));
                empleadoUser.setEmpleado(empleadoEmpleado);
                empleadoUser.setEstaActivo(true);

                userRepo.save(empleadoUser);
            }

            if (userRepo.findByUsername("cliente").isEmpty()) {
                Usuario clienteUser = new Usuario();
                clienteUser.setUsername("cliente");
                clienteUser.setPassword(encoder.encode("cliente123"));
                clienteUser.setEstaActivo(true);

                userRepo.save(clienteUser);
            }
        };
    }

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

    @Bean
    CommandLineRunner initUnidadesMedida(UnidadMedidaRepository unidadMedidaRepository) {
        return args -> {
            if (unidadMedidaRepository.count() == 0) {
                UnidadMedida miligramos = new UnidadMedida();
                miligramos.setDenominacion("Miligramos");

                UnidadMedida gramos = new UnidadMedida();
                gramos.setDenominacion("Gramos");

                UnidadMedida kilogramos = new UnidadMedida();
                kilogramos.setDenominacion("Kilogramos");

                UnidadMedida mililitros = new UnidadMedida();
                mililitros.setDenominacion("Mililitros");

                UnidadMedida litros = new UnidadMedida();
                litros.setDenominacion("Litros");

                UnidadMedida unidades = new UnidadMedida();
                unidades.setDenominacion("Unidades");

                unidadMedidaRepository.saveAll(
                        List.of(miligramos, gramos, kilogramos, mililitros, litros, unidades)
                );
            }
        };
    }

    @Bean
    CommandLineRunner initPaises(PaisRepository paisRepository) {
        return args -> {
            if (paisRepository.count() == 0) {
                Pais argentina = new Pais();
                argentina.setNombre("Argentina");

                Pais chile = new Pais();
                chile.setNombre("Chile");

                Pais uruguay = new Pais();
                uruguay.setNombre("Uruguay");

                paisRepository.saveAll(List.of(argentina, chile, uruguay));
            }
        };
    }
}
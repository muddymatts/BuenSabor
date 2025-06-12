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
    CommandLineRunner initCategoriaInsumos (CategoriaArticuloRepository categoriaArticuloInsumoRepository) {
        return args -> {
            if (categoriaArticuloInsumoRepository.count() == 0) {
                CategoriaArticulo alimentos = new CategoriaArticulo();
                alimentos.setDenominacion("Alimentos");
                alimentos.setCategoriaPadre(null);

                CategoriaArticulo bebidas = new CategoriaArticulo();
                bebidas.setDenominacion("Bebidas");
                bebidas.setCategoriaPadre(null);

                CategoriaArticulo gaseosas = new CategoriaArticulo();
                gaseosas.setDenominacion("Gaseosas");
                gaseosas.setCategoriaPadre(bebidas);

                CategoriaArticulo agua = new CategoriaArticulo();
                agua.setDenominacion("Agua");
                agua.setCategoriaPadre(bebidas);

                CategoriaArticulo vegetales = new CategoriaArticulo();
                vegetales.setDenominacion("Vegetales");
                vegetales.setCategoriaPadre(alimentos);

                CategoriaArticulo lacteos = new CategoriaArticulo();
                lacteos.setDenominacion("Lacteos");
                lacteos.setCategoriaPadre(alimentos);

                CategoriaArticulo quesos = new CategoriaArticulo();
                quesos.setDenominacion("Quesos");
                quesos.setCategoriaPadre(lacteos);

                categoriaArticuloInsumoRepository.saveAll(
                        List.of(alimentos,bebidas,gaseosas, agua, vegetales, lacteos, quesos)
                );
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
    CommandLineRunner initArticuloInsumo(
            ArticuloInsumoRepository articuloInsumoRepository,
            CategoriaArticuloRepository categoriaArticuloInsumoRepository,
            UnidadMedidaRepository unidadMedidaRepository) {
        return args -> {
            if (articuloInsumoRepository.count() == 0) {
                List<CategoriaArticulo> categorias = categoriaArticuloInsumoRepository.findAll();
                List<UnidadMedida> unidades = unidadMedidaRepository.findAll();

                ArticuloInsumo quesoMuzzarella = new ArticuloInsumo();
                quesoMuzzarella.setPrecioCompra(1.5);
                quesoMuzzarella.setUnidadMedida(unidades.get(1));
                quesoMuzzarella.setCategoriaArticulo(categorias.get(6));
                quesoMuzzarella.setDenominacion("Queso Muzzarella");
                quesoMuzzarella.setEsParaElaborar(true);
                quesoMuzzarella.setPrecioVenta(1.75);

                ArticuloInsumo salsaTomate = new ArticuloInsumo();
                salsaTomate.setPrecioCompra(0.5);
                salsaTomate.setUnidadMedida(unidades.get(3));
                salsaTomate.setCategoriaArticulo(categorias.get(4));
                salsaTomate.setDenominacion("Salsa de Tomate");
                salsaTomate.setEsParaElaborar(true);
                salsaTomate.setPrecioVenta(0.75);

                ArticuloInsumo cocaCola = new ArticuloInsumo();
                cocaCola.setPrecioCompra(1000.0);
                cocaCola.setUnidadMedida(unidades.get(4));
                cocaCola.setCategoriaArticulo(categorias.get(2));
                cocaCola.setDenominacion("Coca Cola");
                cocaCola.setEsParaElaborar(false);
                cocaCola.setPrecioVenta(1500.0);

                articuloInsumoRepository.saveAll(
                        List.of(quesoMuzzarella, salsaTomate, cocaCola)
                );
            }
        };

    }

    @Bean
    CommandLineRunner initArticuloManufacturado(
            ArticuloManufacturadoRepository articuloManufacturadoRepository,
            CategoriaArticuloManufacturadoRepository categoriaArticuloManufacturadoRepository,
            ArticuloInsumoRepository articuloInsumoRepository
    ) {
        return args -> {
            if (articuloManufacturadoRepository.count() == 0) {
                List<CategoriaArticuloManufacturado> categorias = categoriaArticuloManufacturadoRepository.findAll();

                if (!categorias.isEmpty()) {
                    ArticuloManufacturado pizzaMuzzarella = new ArticuloManufacturado();
                    pizzaMuzzarella.setDenominacion("Pizza Muzzarella");
                    pizzaMuzzarella.setDescripcion("Pizza con mozzarella y salsa de tomate");
                    pizzaMuzzarella.setCategoria(categorias.get(0));
                    pizzaMuzzarella.setPrecioVenta(2500.0);
                    pizzaMuzzarella.setTiempoEstimado(30);


                    ArticuloManufacturado hamburguesaClasica = new ArticuloManufacturado();
                    hamburguesaClasica.setDenominacion("Hamburguesa Clásica");
                    hamburguesaClasica.setDescripcion("Hamburguesa con carne vacuna y vegetales frescos");
                    hamburguesaClasica.setCategoria(categorias.get(1));
                    hamburguesaClasica.setPrecioVenta(1500.0);
                    hamburguesaClasica.setTiempoEstimado(20);

                    ArticuloManufacturado lomitoSimple = new ArticuloManufacturado();
                    lomitoSimple.setDenominacion("Lomito Simple");
                    lomitoSimple.setDescripcion("Lomito con jamón y queso");
                    lomitoSimple.setCategoria(categorias.get(3));
                    lomitoSimple.setPrecioVenta(1000.0);
                    lomitoSimple.setTiempoEstimado(25);

                    articuloManufacturadoRepository.saveAll(List.of(pizzaMuzzarella, hamburguesaClasica, lomitoSimple));
                }
            }
        };
    }

    @Bean
    CommandLineRunner initArticuloManufacturadoDetalles(
            ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository,
            ArticuloManufacturadoRepository articuloManufacturadoRepository,
            ArticuloInsumoRepository articuloInsumoRepository
    ) {
        return args -> {
            if (articuloManufacturadoDetalleRepository.count() == 0) {
                List<ArticuloManufacturado> articulos = articuloManufacturadoRepository.findAll();
                List<ArticuloInsumo> insumos = articuloInsumoRepository.findAll();

                ArticuloManufacturadoDetalle pizzaMuzzarellaDetalles = new ArticuloManufacturadoDetalle();
                pizzaMuzzarellaDetalles.setCantidad(500);
                pizzaMuzzarellaDetalles.setManufacturado(articulos.get(0));
                pizzaMuzzarellaDetalles.setInsumo(insumos.get(0));

                ArticuloManufacturadoDetalle pizzaMuzzarellaDetalles1 = new ArticuloManufacturadoDetalle();
                pizzaMuzzarellaDetalles1.setCantidad(250);
                pizzaMuzzarellaDetalles1.setManufacturado(articulos.get(0));
                pizzaMuzzarellaDetalles1.setInsumo(insumos.get(1));

                articuloManufacturadoDetalleRepository.saveAll(List.of(pizzaMuzzarellaDetalles, pizzaMuzzarellaDetalles1));
            }
        };
    }
}
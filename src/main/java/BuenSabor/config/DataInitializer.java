package BuenSabor.config;

import BuenSabor.enums.Rol;
import BuenSabor.model.*;
import BuenSabor.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.logging.Logger;
import java.util.logging.Level;

@Configuration
public class DataInitializer {

    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    private final int idArgentina = 1;
    private final int idChile = 2;
    private final int idUruguay = 3;

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

    @Bean
    @Order(1)
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

    @Bean
    @Order(2)
    CommandLineRunner initProvincias(ProvinciaRepository provinciaRepository, PaisRepository paisRepository, ResourceLoader resourceLoader) {
        return args -> {
            if (provinciaRepository.count() == 0) {
                Resource resource = resourceLoader.getResource("classpath:data/provincias.json");
                ObjectMapper objectMapper = new ObjectMapper();
                TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<>() {
                };

                try (InputStream inputStream = resource.getInputStream()) {
                    List<Map<String, Object>> provinciasList = objectMapper.readValue(inputStream, typeReference);
                    List<Provincia> provincias = new ArrayList<>();

                    for (Map<String, Object> provinciaJson : provinciasList) {
                        Provincia provincia = new Provincia();
                        provincia.setNombre((String) provinciaJson.get("nombre"));

                        int paisId = (Integer) provinciaJson.get("pais_id");
                        Pais pais = paisRepository.findById(paisId)
                                .orElseThrow(() -> new IllegalArgumentException("No se encontró el país con ID: " + paisId));

                        provincia.setPais(pais);
                        provincias.add(provincia);
                    }

                    provinciaRepository.saveAll(provincias);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error al inicializar provincias.", e);
                }
            }
        };
    }

    @Bean
    @Order(3)
    CommandLineRunner initLocalidadesArgentina(LocalidadRepository localidadRepository, ProvinciaRepository provinciaRepository) {
        return args -> {
            try {
                boolean localidadesArgentinaCargadas = provinciaRepository.findByPaisId(idArgentina).stream()
                        .anyMatch(provincia -> !localidadRepository.findByProvinciaId(provincia.getId()).isEmpty());

                if (!localidadesArgentinaCargadas) {
                    InputStream is = getClass().getResourceAsStream("/data/localidades_argentina.json");

                    if (is == null) {
                        throw new RuntimeException("Archivo 'localidades_argentina.json' no encontrado");
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(is);
                    JsonNode departamentosNode = rootNode.get("departamentos");

                    if (departamentosNode.isArray()) {
                        for (JsonNode departamentoNode : departamentosNode) {
                            String nombreDepartamento = departamentoNode.get("nombre").asText();
                            String nombreProvincia = departamentoNode.get("provincia").get("nombre").asText();

                            Optional<Provincia> provinciaOpt = provinciaRepository.findByNombreAndPaisId(nombreProvincia, idArgentina);

                            if (provinciaOpt.isPresent()) {
                                Provincia provincia = provinciaOpt.get();

                                Localidad localidad = new Localidad();
                                localidad.setNombre(nombreDepartamento);
                                localidad.setProvincia(provincia);

                                localidadRepository.save(localidad);
                            } else {
                                System.out.println("Provincia no encontrada para pais_id = 1, nombre: " + nombreProvincia);
                            }
                        }
                    }
                } else {
                    logger.info("Las localidades de Argentina ya están cargadas en la base de datos.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al inicializar los datos de localidades de Argentina.", e);
            }
        };
    }

    @Bean
    @Order(4)
    CommandLineRunner initLocalidadesChile(LocalidadRepository localidadRepository, ProvinciaRepository provinciaRepository) {
        return args -> {
            try {
                boolean localidadesChileCargadas = provinciaRepository.findByPaisId(idChile).stream()
                        .anyMatch(provincia -> !localidadRepository.findByProvinciaId(provincia.getId()).isEmpty());

                if (!localidadesChileCargadas) {
                    InputStream is = getClass().getResourceAsStream("/data/localidades_chile.json");

                    if (is == null) {
                        throw new RuntimeException("Archivo 'localidades_chile.json' no encontrado");
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(is);
                    JsonNode regionesNode = rootNode.get("regiones");

                    if (regionesNode.isArray()) {
                        for (JsonNode regionNode : regionesNode) {
                            String nombreRegion = regionNode.get("region").asText();
                            JsonNode comunasNode = regionNode.get("comunas");

                            if (comunasNode.isArray()) {
                                for (JsonNode comunaNode : comunasNode) {
                                    String nombreComuna = comunaNode.asText();

                                    Optional<Provincia> provinciaOpt = provinciaRepository.findByNombreAndPaisId(nombreRegion, idChile);

                                    if (provinciaOpt.isPresent()) {
                                        Provincia provincia = provinciaOpt.get();

                                        Localidad localidad = new Localidad();
                                        localidad.setNombre(nombreComuna);
                                        localidad.setProvincia(provincia);

                                        localidadRepository.save(localidad);
                                    } else {
                                        System.err.println("Provincia no encontrada en Chile (pais_id=" + idChile + ") con nombre: " + nombreRegion);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    logger.info("Las localidades de Chile ya están cargadas en la base de datos.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al inicializar los datos de localidades de Chile.", e);
            }
        };
    }

    @Bean
    @Order(5)
    CommandLineRunner initLocalidadesUruguay(LocalidadRepository localidadRepository, ProvinciaRepository provinciaRepository) {
        return args -> {
            try {
                boolean localidadesUruguayCargadas = provinciaRepository.findByPaisId(idUruguay).stream()
                        .anyMatch(provincia -> !localidadRepository.findByProvinciaId(provincia.getId()).isEmpty());

                if (!localidadesUruguayCargadas) {
                    InputStream is = getClass().getResourceAsStream("/data/localidades_uruguay.json");

                    if (is == null) {
                        throw new RuntimeException("Archivo 'localidades_uruguay.json' no encontrado");
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(is);

                    if (rootNode.isArray()) {
                        for (JsonNode regionNode : rootNode) {
                            String nombreRegion = regionNode.get("name").asText();
                            JsonNode townsNode = regionNode.get("towns");

                            if (townsNode.isArray()) {
                                for (JsonNode townNode : townsNode) {
                                    String nombreLocalidad = townNode.get("name").asText();

                                    Optional<Provincia> provinciaOpt = provinciaRepository.findByNombreAndPaisId(nombreRegion, idUruguay);

                                    if (provinciaOpt.isPresent()) {
                                        Provincia provincia = provinciaOpt.get();

                                        Localidad localidad = new Localidad();
                                        localidad.setNombre(nombreLocalidad);
                                        localidad.setProvincia(provincia);

                                        localidadRepository.save(localidad);
                                    } else {
                                        System.err.println("Provincia no encontrada en Uruguay (pais_id=" + idUruguay + ") con nombre: " + nombreRegion);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    logger.info("Las localidades de Uruguay ya están cargadas en la base de datos.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al inicializar los datos de localidades de Uruguay.", e);
            }
        };
    }
}
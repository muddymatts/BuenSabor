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
                    e.printStackTrace();
                }
            }
        };
    }

    @Bean
    @Order(3)
    CommandLineRunner initLocalidadesArgentina(LocalidadRepository localidadRepository, ProvinciaRepository provinciaRepository) {
        return args -> {
            try {
                if (localidadRepository.count() == 0) {
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

                            Optional<Provincia> provinciaOpt = provinciaRepository.findByNombreAndPaisId(nombreProvincia, 1);

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
                    System.out.println("La tabla 'localidad' ya contiene datos, no se realizaron inserciones.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al inicializar los datos de localidades.");
            }
        };
    }

    @Bean
    @Order(4)
    CommandLineRunner initLocalidadesChile(LocalidadRepository localidadRepository, ProvinciaRepository provinciaRepository) {
        return args -> {
            try {
                boolean localidadesChileCargadas = provinciaRepository.findByPaisId(2).stream()
                        .anyMatch(provincia -> !localidadRepository.findByProvinciaId(provincia.getId()).isEmpty());

                if (!localidadesChileCargadas) {
                    InputStream is = getClass().getResourceAsStream("/data/localidades_chile.json");

                    if (is == null) {
                        throw new RuntimeException("Archivo 'localidades_chile.json' no encontrado");
                    }

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(is);
                    JsonNode regionesNode = rootNode.get("regiones");

                    int chilePaisId = 2;

                    if (regionesNode.isArray()) {
                        for (JsonNode regionNode : regionesNode) {
                            String nombreRegion = regionNode.get("region").asText();
                            JsonNode comunasNode = regionNode.get("comunas");

                            if (comunasNode.isArray()) {
                                for (JsonNode comunaNode : comunasNode) {
                                    String nombreComuna = comunaNode.asText();

                                    Optional<Provincia> provinciaOpt = provinciaRepository.findByNombreAndPaisId(nombreRegion, chilePaisId);

                                    if (provinciaOpt.isPresent()) {
                                        Provincia provincia = provinciaOpt.get();

                                        Localidad localidad = new Localidad();
                                        localidad.setNombre(nombreComuna);
                                        localidad.setProvincia(provincia);

                                        localidadRepository.save(localidad);
                                    } else {
                                        System.err.println("Provincia no encontrada en Chile (pais_id=" + chilePaisId + ") con nombre: " + nombreRegion);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Las localidades de Chile ya están cargadas en la base de datos.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al inicializar los datos de localidades de Chile.");
            }
        };
    }
}
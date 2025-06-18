package BuenSabor.config;

import BuenSabor.enums.Rol;
import BuenSabor.model.*;
import BuenSabor.repository.*;
import BuenSabor.service.initDB.InitArticulosService;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class DataInitializer {

    private final InitArticulosService initArticulosService;

    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());

    private final int idArgentina = 1;
    private final int idChile = 2;
    private final int idUruguay = 3;

    public DataInitializer(InitArticulosService initArticulosService) {
        this.initArticulosService = initArticulosService;
    }

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
    CommandLineRunner initInsumosYProductos() {
        return args -> {
            // Insumos
            initArticulosService.setupUnidadesMedida();
            initArticulosService.setupCategoriaInsumos();
            initArticulosService.setupArticulosInsumo();
            // Manufacturados
            initArticulosService.setupCategoriasManufacturados();
            initArticulosService.setupArticulosManufacturados();
            initArticulosService.setupArticuloManufacturadoDetalles();
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
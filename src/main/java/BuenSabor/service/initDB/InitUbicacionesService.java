package BuenSabor.service.initDB;

import BuenSabor.model.Localidad;
import BuenSabor.model.Pais;
import BuenSabor.model.Provincia;
import BuenSabor.repository.LocalidadRepository;
import BuenSabor.repository.PaisRepository;
import BuenSabor.repository.ProvinciaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InitUbicacionesService {

    private static final String FIELD_NOMBRE = "nombre";
    private static final String FIELD_PAIS_ID = "pais_id";

    private final PaisRepository paisRepository;
    private final ProvinciaRepository provinciaRepository;
    private final LocalidadRepository localidadRepository;
    private final ResourceLoader resourceLoader;

    private static final Logger logger = Logger.getLogger(InitUbicacionesService.class.getName());

    public InitUbicacionesService(PaisRepository paisRepository,
                                  ProvinciaRepository provinciaRepository,
                                  LocalidadRepository localidadRepository,
                                  ResourceLoader resourceLoader) {
        this.paisRepository = paisRepository;
        this.provinciaRepository = provinciaRepository;
        this.localidadRepository = localidadRepository;
        this.resourceLoader = resourceLoader;
    }

    @Transactional
    public void setupPaises() {
        try {
            if (paisRepository.count() == 0) {
                Pais argentina = new Pais();
                argentina.setNombre("Argentina");

                Pais chile = new Pais();
                chile.setNombre("Chile");

                Pais uruguay = new Pais();
                uruguay.setNombre("Uruguay");

                paisRepository.saveAll(List.of(argentina, chile, uruguay));
                logger.info("Paises cargados exitosamente.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al inicializar los datos de paises.", e);
        }
    }

    @Transactional
    public void setupProvincias() {
        try {
            if (provinciaRepository.count() == 0) {
                List<Provincia> provincias = loadProvinciasFromJson();
                provinciaRepository.saveAll(provincias);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al inicializar los datos de provincias.", e);
        }
    }

    @Transactional
    public void setupLocalidades() {
        try {
            setupLocalidadesPorPais(1, "argentina");
            setupLocalidadesPorPais(2, "chile");
            setupLocalidadesPorPais(3, "uruguay");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al inicializar los datos de localidades.", e);
        }
    }

    private List<Provincia> loadProvinciasFromJson() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:data/ubicaciones/provincias.json");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Map<String, Object>>> typeReference = new TypeReference<>() {
        };

        try (InputStream inputStream = resource.getInputStream()) {
            List<Map<String, Object>> provinciasList = objectMapper.readValue(inputStream, typeReference);
            return mapProvincias(provinciasList);
        }
    }

    private List<Provincia> mapProvincias(List<Map<String, Object>> provinciasList) {
        List<Provincia> provincias = new ArrayList<>();
        for (Map<String, Object> provinciaJson : provinciasList) {
            provincias.add(mapToProvincia(provinciaJson));
        }
        return provincias;
    }

    private Provincia mapToProvincia(Map<String, Object> provinciaJson) {
        Provincia provincia = new Provincia();
        provincia.setNombre((String) provinciaJson.get(FIELD_NOMBRE));

        int paisId = (Integer) provinciaJson.get(FIELD_PAIS_ID);
        Pais pais = paisRepository.findById(paisId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el país con ID: " + paisId));
        provincia.setPais(pais);

        return provincia;
    }

    private void setupLocalidadesPorPais(int paisId, String paisNombre) {
        try {
            boolean localidadesCargadas = provinciaRepository.findByPaisId(paisId).stream()
                    .anyMatch(provincia -> !localidadRepository.findByProvinciaId(provincia.getId()).isEmpty());

            if (localidadesCargadas) {
                logger.info("Las localidades de " + paisNombre + " ya están cargadas en la base de datos.");
                return;
            }

            // carga el archivo dinamicamente
            String ruta = String.format("/data/ubicaciones/localidades_%s.json", paisNombre.toLowerCase());
            InputStream is = getClass().getResourceAsStream(ruta);

            if (is == null) {
                throw new RuntimeException("Archivo '" + ruta + "' no encontrado");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(is);

            switch (paisNombre.toLowerCase()) {
                case "argentina" -> procesarArgentina(rootNode, paisId);
                case "chile" -> procesarChile(rootNode, paisId);
                case "uruguay" -> procesarUruguay(rootNode, paisId);
                default -> throw new IllegalArgumentException("País no soportado: " + paisNombre);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al inicializar los datos de localidades de " + paisNombre, e);
        }
    }

    private void procesarArgentina(JsonNode rootNode, int paisId) {
        JsonNode departamentosNode = rootNode.get("departamentos");
        if (departamentosNode != null && departamentosNode.isArray()) {
            for (JsonNode departamentoNode : departamentosNode) {
                String nombreDepartamento = departamentoNode.get("nombre").asText();
                String nombreProvincia = departamentoNode.get("provincia").get("nombre").asText();

                provinciaRepository.findByNombreAndPaisId(nombreProvincia, paisId).ifPresentOrElse(
                        provincia -> {
                            Localidad localidad = new Localidad();
                            localidad.setNombre(nombreDepartamento);
                            localidad.setProvincia(provincia);
                            localidadRepository.save(localidad);
                        },
                        () -> System.err.println("Provincia no encontrada en Argentina con nombre: " + nombreProvincia)
                );
            }
        }
    }

    private void procesarChile(JsonNode rootNode, int paisId) {
        JsonNode regionesNode = rootNode.get("regiones");
        if (regionesNode != null && regionesNode.isArray()) {
            for (JsonNode regionNode : regionesNode) {
                String nombreRegion = regionNode.get("region").asText();
                JsonNode comunasNode = regionNode.get("comunas");

                if (comunasNode != null && comunasNode.isArray()) {
                    for (JsonNode comunaNode : comunasNode) {
                        String nombreComuna = comunaNode.asText();

                        provinciaRepository.findByNombreAndPaisId(nombreRegion, paisId).ifPresentOrElse(
                                provincia -> {
                                    Localidad localidad = new Localidad();
                                    localidad.setNombre(nombreComuna);
                                    localidad.setProvincia(provincia);
                                    localidadRepository.save(localidad);
                                },
                                () -> System.err.println("Provincia no encontrada en Chile con nombre: " + nombreRegion)
                        );
                    }
                }
            }
        }
    }

    private void procesarUruguay(JsonNode rootNode, int paisId) {
        if (rootNode != null && rootNode.isArray()) {
            for (JsonNode regionNode : rootNode) {
                String nombreRegion = regionNode.get("name").asText();
                JsonNode townsNode = regionNode.get("towns");

                if (townsNode != null && townsNode.isArray()) {
                    for (JsonNode townNode : townsNode) {
                        String nombreLocalidad = townNode.get("name").asText();

                        provinciaRepository.findByNombreAndPaisId(nombreRegion, paisId).ifPresentOrElse(
                                provincia -> {
                                    Localidad localidad = new Localidad();
                                    localidad.setNombre(nombreLocalidad);
                                    localidad.setProvincia(provincia);
                                    localidadRepository.save(localidad);
                                },
                                () -> System.err.println("Provincia no encontrada en Uruguay con nombre: " + nombreRegion)
                        );
                    }
                }
            }
        }
    }
}

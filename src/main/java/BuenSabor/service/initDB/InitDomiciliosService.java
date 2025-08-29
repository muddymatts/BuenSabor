package BuenSabor.service.initDB;

import BuenSabor.dto.jsonMapperDtos.DomicilioJsonDTO;
import BuenSabor.model.Domicilio;
import BuenSabor.model.Localidad;
import BuenSabor.repository.DomicilioRepository;
import BuenSabor.repository.LocalidadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InitDomiciliosService {

    private final DomicilioRepository domicilioRepository;
    private final LocalidadRepository localidadRepository;

    private static final Logger logger = Logger.getLogger(InitDomiciliosService.class.getName());

    public InitDomiciliosService(LocalidadRepository localidadRepository, DomicilioRepository domicilioRepository) {
        this.localidadRepository = localidadRepository;
        this.domicilioRepository = domicilioRepository;
    }

    @Transactional
    public void setupDomicilios() {
        try {
            if (domicilioRepository.count() == 0) {
                InputStream inputStream = getClass().getResourceAsStream("/data/ubicaciones/domicilios.json");

                if (inputStream == null) {
                    throw new RuntimeException("Archivo 'domicilios.json' no encontrado.");
                }

                ObjectMapper objectMapper = new ObjectMapper();

                List<DomicilioJsonDTO> domiciliosDto = objectMapper.readValue(inputStream,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, DomicilioJsonDTO.class));

                List<Domicilio> domicilios = domiciliosDto.stream().map(dto -> {
                    Domicilio domicilio = new Domicilio();

                    domicilio.setCalle(dto.getCalle());
                    domicilio.setNumero(dto.getNumero());
                    domicilio.setCp(dto.getCp());

                    Localidad localidad = localidadRepository.findById(dto.getLocalidadId())
                            .orElseThrow(() -> new RuntimeException("Localidad no encontrada con id: " + dto.getLocalidadId()));

                    domicilio.setLocalidad(localidad);

                    return domicilio;
                }).collect(Collectors.toList());

                domicilioRepository.saveAll(domicilios);
                logger.info("Domicilios cargados exitosamente desde 'domicilios.json'");
            }

        } catch (Exception e) {
            logger.severe("Error al inicializar domicilios: " + e.getMessage());
        }
    }
}

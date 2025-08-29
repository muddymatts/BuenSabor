package BuenSabor.service.initDB;

import BuenSabor.dto.jsonMapperDtos.EmpresaJsonDTO;
import BuenSabor.model.Empresa;
import BuenSabor.repository.empresa.EmpresaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InitEmpresasService {

    private final EmpresaRepository empresaRepository;

    private static final Logger logger = Logger.getLogger(InitEmpresasService.class.getName());

    public InitEmpresasService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public void setupEmpresas() {
        try {
            if (empresaRepository.count() == 0) {
                InputStream inputStream = getClass().getResourceAsStream("/data/empresas/empresas.json");

                if (inputStream == null) {
                    throw new RuntimeException("Archivo 'empresas.json' no encontrado.");
                }

                ObjectMapper objectMapper = new ObjectMapper();
                List<EmpresaJsonDTO> empresasDto = objectMapper.readValue(inputStream,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, EmpresaJsonDTO.class));

                List<Empresa> empresas = empresasDto.stream().map(dto -> {
                    Empresa empresa = new Empresa();

                    empresa.setFechaAlta(dto.getFechaAlta() != null ? LocalDate.parse(dto.getFechaAlta()) : null);
                    empresa.setFechaBaja(dto.getFechaBaja() != null ? LocalDate.parse(dto.getFechaBaja()) : null);

                    empresa.setCuil(dto.getCuil());
                    empresa.setNombre(dto.getNombre());
                    empresa.setRazonSocial(dto.getRazonSocial());

                    return empresa;
                }).collect(Collectors.toList());

                empresaRepository.saveAll(empresas);
                logger.info("Empresas cargadas exitosamente desde 'empresas.json'");
            } else {
                logger.info("No se cargaron empresas, ya existen datos en la base de datos.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al cargar empresas desde archivo JSON.", e);

        }
    }
}

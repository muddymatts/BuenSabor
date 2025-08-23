package BuenSabor.service.initDB;

import BuenSabor.dto.jsonMapperDtos.SucursalJsonDTO;
import BuenSabor.model.Domicilio;
import BuenSabor.model.Empresa;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.repository.DomicilioRepository;
import BuenSabor.repository.empresa.EmpresaRepository;
import BuenSabor.repository.sucursal.SucursalEmpresaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InitSucursalesService {

    private final SucursalEmpresaRepository sucursalEmpresaRepository;
    private final DomicilioRepository domicilioRepository;
    private final EmpresaRepository empresaRepository;

    private static final Logger logger = Logger.getLogger(InitSucursalesService.class.getName());

    public InitSucursalesService(SucursalEmpresaRepository sucursalEmpresaRepository,
                                 DomicilioRepository domicilioRepository,
                                 EmpresaRepository empresaRepository) {
        this.sucursalEmpresaRepository = sucursalEmpresaRepository;
        this.domicilioRepository = domicilioRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public void setupSucursales() {
        try {
            if (sucursalEmpresaRepository.count() == 0) {
                InputStream inputStream = getClass().getResourceAsStream("/data/empresas/sucursales.json");

                if (inputStream == null) {
                    throw new RuntimeException("Archivo 'sucursales.json' no encontrado.");
                }

                ObjectMapper objectMapper = new ObjectMapper();

                List<SucursalJsonDTO> sucursalesDto = objectMapper.readValue(inputStream,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, SucursalJsonDTO.class));

                List<SucursalEmpresa> sucursales = sucursalesDto.stream().map(dto -> {
                    SucursalEmpresa sucursal = new SucursalEmpresa();

                    sucursal.setFechaAlta(dto.getFechaAlta() != null ? LocalDate.parse(dto.getFechaAlta()) : null);
                    sucursal.setFechaBaja(dto.getFechaBaja() != null ? LocalDate.parse(dto.getFechaBaja()) : null);

                    sucursal.setHorarioApertura(dto.getHorarioApertura());
                    sucursal.setHorarioCierre(dto.getHorarioCierre());
                    sucursal.setNombre(dto.getNombre());

                    Domicilio domicilio = domicilioRepository.findById(dto.getDomicilioId().longValue())
                            .orElseThrow(() -> new RuntimeException("Domicilio no encontrado con ID: " + dto.getDomicilioId()));
                    sucursal.setDomicilio(domicilio);
                    
                    Empresa empresa = empresaRepository.findById(dto.getEmpresaId().longValue())
                            .orElseThrow(() -> new RuntimeException("Empresa no encontrada con ID: " + dto.getEmpresaId()));
                    sucursal.setEmpresa(empresa);

                    return sucursal;
                }).collect(Collectors.toList());

                sucursalEmpresaRepository.saveAll(sucursales);
                logger.info("Sucursales cargadas exitosamente desde 'sucursales.json'");
            } else {
                logger.info("Sucursales ya cargadas.");
            }
        } catch (Exception e) {
            logger.severe("Error al cargar sucursales desde archivo JSON: " + e.getMessage());
            throw new RuntimeException("Error al cargar sucursales.", e);
        }
    }
}

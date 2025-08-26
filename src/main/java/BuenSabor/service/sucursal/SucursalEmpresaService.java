package BuenSabor.service.sucursal;

import BuenSabor.dto.articuloManufacturado.ArticuloManufacturadoDTO;
import BuenSabor.dto.articuloManufacturado.ArticulosManufacturadosDisponiblesDTO;
import BuenSabor.dto.promocion.PromocionDTO;
import BuenSabor.dto.promocion.PromocionesDisponiblesDTO;
import BuenSabor.dto.sucursal.CantidadDisponibleDTO;
import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import BuenSabor.mapper.SucursalInsumoMapper;
import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.Cliente;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.model.SucursalInsumo;
import BuenSabor.repository.ArticuloInsumoRepository;
import BuenSabor.repository.ClienteRepository;
import BuenSabor.repository.sucursal.SucursalEmpresaRepository;
import BuenSabor.repository.sucursal.SucursalInsumoRepository;
import BuenSabor.service.ArticuloManufacturadoService;
import BuenSabor.service.BajaLogicaService;
import BuenSabor.service.articuloInsumo.ArticuloInsumoService;
import BuenSabor.service.promocion.PromocionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SucursalEmpresaService extends BajaLogicaService {

    private final SucursalEmpresaRepository repository;
    private final SucursalInsumoRepository sucursalInsumoRepository;
    private final ArticuloInsumoRepository articuloInsumoRepository;
    private final SucursalInsumoMapper sucursalInsumoMapper;
    private final ArticuloInsumoService articuloInsumoService;
    private final ArticuloManufacturadoService articuloManufacturadoService;
    private final PromocionService promocionService;
    private final ClienteRepository clienteRepository;

    public SucursalEmpresaService (
            SucursalEmpresaRepository repository,
            SucursalInsumoRepository stockRepository,
            ArticuloInsumoRepository articuloInsumoRepository,
            SucursalInsumoMapper sucursalInsumoMapper,
            ArticuloInsumoService articuloInsumoService,
            ArticuloManufacturadoService articuloManufacturadoService,
            PromocionService promocionService,
            ClienteRepository clienteRepository){
        this.repository = repository;
        this.sucursalInsumoRepository = stockRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.sucursalInsumoMapper = sucursalInsumoMapper;
        this.articuloInsumoService = articuloInsumoService;
        this.articuloManufacturadoService = articuloManufacturadoService;
        this.promocionService = promocionService;
        this.clienteRepository = clienteRepository;
    }

    public SucursalEmpresa guardar(SucursalEmpresa sucursal) {
        return repository.save(sucursal);
    }

    public SucursalEmpresa getSucursal(Long id) { return repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Sucursal no encontrada, id: " + id + ".")); }

    public List<SucursalInsumoDTO> getStock(Long id) {
        List<SucursalInsumoDTO> stock = repository.getStock(id);
        stock.forEach(item -> {
            ArticuloInsumo articulo = articuloInsumoRepository.getReferenceById(item.getIdInsumo());

            item.setCategorias(articuloInsumoService.getCategogoriasAnidadas(articulo.getCategoriaArticuloInsumo(),item.getCategorias()));
        });
        return stock;
    }

    public SucursalInsumoDTO setStock(Long id, SucursalInsumoDTO stock) {
        // 1. Buscar la sucursal
        SucursalEmpresa sucursal = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sucursal no encontrada, id: " + id + "."));

        // 2. Buscar el insumo
        ArticuloInsumo insumo = articuloInsumoRepository.findById(stock.getIdInsumo())
                .orElseThrow(()-> new RuntimeException("Articulo no encontrado, id: " + stock.getIdInsumo() + "."));

        // 3. Verificar si ya existe el stock para esa combinación
        Optional<SucursalInsumo> existente = sucursalInsumoRepository.findBySucursalAndInsumo(sucursal, insumo);

        SucursalInsumo sockSucursalInsumo;

        if (existente.isPresent()) {
            // Actualiza los valores existentes
            sockSucursalInsumo = existente.get();
        } else {
            // Crea uno nuevo
            sockSucursalInsumo = new SucursalInsumo();
            sockSucursalInsumo.setSucursal(sucursal);
            sockSucursalInsumo.setInsumo(insumo);
        }

        // 4. Setear los valores desde el DTO
        sockSucursalInsumo.setStockActual(stock.getCantidadActual());
        sockSucursalInsumo.setStockMinimo(stock.getCantidadMinima());
        sockSucursalInsumo.setStockMaximo(stock.getCantidadMaxima());

        // 5. Guardar
        sucursalInsumoRepository.save(sockSucursalInsumo);

        return sucursalInsumoMapper.toDTO(sockSucursalInsumo);

    }

    public List<ArticulosManufacturadosDisponiblesDTO> getProducts(Long id) {
        // 1. Buscar la sucursal
        SucursalEmpresa sucursal = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sucursal no encontrada, id: " + id + "."));

        List<CantidadDisponibleDTO> disponiblesEnSucursal = repository.getCantidadDisponible(sucursal.getId());

        Map<Long, Integer> mapCantidadDisponible = disponiblesEnSucursal.stream()
                .collect(Collectors.toMap(CantidadDisponibleDTO::getId, CantidadDisponibleDTO::getCantidadDisponible));

        List<ArticuloManufacturadoDTO> listaArticulos = articuloManufacturadoService.getArticulosManufacturadoDTO();

        return listaArticulos.stream()
                .map(articulo -> {
                    ArticulosManufacturadosDisponiblesDTO dto = new ArticulosManufacturadosDisponiblesDTO();
                    dto.setId(articulo.getId());
                    dto.setDenominacion(articulo.getDenominacion());
                    dto.setFechaBaja(articulo.getFechaBaja());
                    dto.setIngredientes(articulo.getIngredientes());
                    dto.setListaImagenes(articulo.getListaImagenes());
                    dto.setCantidadDisponible(mapCantidadDisponible.getOrDefault(articulo.getId(), 0));
                    return dto;
                }).toList();
    }

    public List<PromocionesDisponiblesDTO> getPromos(Long id) {
        SucursalEmpresa sucursal = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sucursal no encontrada, id: " + id + "."));

        List<CantidadDisponibleDTO> disponiblesEnSucursal = repository.getCantidadDisponiblePromos(sucursal.getId());

        Map<Long, Integer> mapCantidadDisponible = disponiblesEnSucursal.stream()
                .collect(Collectors.toMap(CantidadDisponibleDTO::getId, CantidadDisponibleDTO::getCantidadDisponible));

        List<PromocionDTO> listaPromociones = (List<PromocionDTO>) promocionService.findAll();

        return listaPromociones.stream()
                .map(promocionDTO -> {
                    PromocionesDisponiblesDTO dto = new PromocionesDisponiblesDTO();
                    dto.setId(promocionDTO.getId());
                    dto.setDenominacion(promocionDTO.getDenominacion());
                    dto.setPrecioVenta(promocionDTO.getPrecioVenta());
                    dto.setDetalle(promocionDTO.getDetalle());
                    dto.setDescuento(promocionDTO.getDescuento());
                    dto.setFechaDesde(promocionDTO.getFechaDesde());
                    dto.setFechaHasta(promocionDTO.getFechaHasta());
                    dto.setImagenes(promocionDTO.getImagenes());
                    dto.setCantidad(mapCantidadDisponible.getOrDefault(promocionDTO.getId(), 0));
                    return dto;
                }).toList();
    }

    public SucursalInsumoDTO addStock(Long id, Long idInsumo, Integer cantidad) {
        // 1. Buscar la sucursal
        SucursalEmpresa sucursal = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sucursal no encontrada, id: " + id + "."));

        // 2. Buscar el insumo
        ArticuloInsumo insumo = articuloInsumoRepository.findById(idInsumo)
                .orElseThrow(()-> new RuntimeException("Articulo no encontrado, id: " +idInsumo + "."));

        // 3. Verificar si ya existe el stock para esa combinación
        Optional<SucursalInsumo> existente = sucursalInsumoRepository.findBySucursalAndInsumo(sucursal, insumo);

        SucursalInsumo sucursalInsumo;

        if (existente.isPresent()) {
            // Actualiza los valores existentes
            sucursalInsumo = existente.get();
        } else {
            throw new RuntimeException("No existe el stock para la sucursal y el insumo especificados.");
        }

        // 4. Setear los valores desde el DTO
        sucursalInsumo.setStockActual(sucursalInsumo.getStockActual() + cantidad);

        // 5. Guardar
        sucursalInsumoRepository.save(sucursalInsumo);

        return sucursalInsumoMapper.toDTO(sucursalInsumo);
    }

    public List<SucursalEmpresa> findAll() {
        return repository.findAll();
    }

    public SucursalEmpresa editarSucursal(SucursalEmpresa sucursal) {
        if (sucursal.getId() == null) throw new RuntimeException("no es posible editar una sucursal sin id");
        return repository.save(sucursal);
    }

    public List<SucursalEmpresa> findByCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(()-> new RuntimeException("Cliente no encontrado con id: " + id));
        return repository.findByDomicilio_Localidad_Provincia(cliente.getDomicilio().getLocalidad().getProvincia());
    }
}

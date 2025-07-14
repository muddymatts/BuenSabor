package BuenSabor.service.sucursal;

import BuenSabor.dto.articuloManufacturado.ArticulosManufacturadosDisponiblesDTO;
import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import BuenSabor.mapper.ProductosMapper;
import BuenSabor.mapper.SucursalInsumoMapper;
import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.model.SucursalInsumo;
import BuenSabor.repository.ArticuloInsumoRepository;
import BuenSabor.repository.sucursal.SucursalEmpresaRepository;
import BuenSabor.repository.sucursal.SucursalInsumoRepository;
import BuenSabor.service.articuloInsumo.ArticuloInsumoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalEmpresaService {

    private final SucursalEmpresaRepository repository;
    private final SucursalInsumoRepository sucursalInsumoRepository;
    private final ArticuloInsumoRepository articuloInsumoRepository;
    private final SucursalInsumoMapper sucursalInsumoMapper;
    private final ProductosMapper productosMapper;
    private final ArticuloInsumoService articuloInsumoService;

    public SucursalEmpresaService (
            SucursalEmpresaRepository repository,
            SucursalInsumoRepository stockRepository,
            ArticuloInsumoRepository articuloInsumoRepository,
            SucursalInsumoMapper sucursalInsumoMapper,
            ProductosMapper productosMapper, ArticuloInsumoService articuloInsumoService){
        this.repository = repository;
        this.sucursalInsumoRepository = stockRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.sucursalInsumoMapper = sucursalInsumoMapper;
        this.productosMapper = productosMapper;
        this.articuloInsumoService = articuloInsumoService;
    }

    public SucursalEmpresa guardar(SucursalEmpresa sucursal) {
        return repository.save(sucursal);
    }

    public SucursalEmpresa getSucursal(Long id) { return repository.getReferenceById(id); }

    public List<SucursalInsumoDTO> getStock(Long id) {
        List<SucursalInsumoDTO> stock = repository.getStock(id);
        stock.stream().map(item -> {
            ArticuloInsumo articulo = articuloInsumoRepository.getReferenceById(item.getIdInsumo());

            item.setCategorias(articuloInsumoService.getCategogoriasAnidadas(articulo.getCategoriaArticuloInsumo(),item.getCategorias()));
            return item.getDenominacion();
        }
        ).toList();
        return stock;
    }

    public SucursalInsumoDTO addStock(Long id, SucursalInsumoDTO stock) {
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

        List<ArticulosManufacturadosDisponiblesDTO> lista = repository.getProducts(id);

        return lista.stream().map(productosMapper::toDTO).toList();
    }
}

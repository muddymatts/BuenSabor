package BuenSabor.service;

import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import BuenSabor.model.ArticuloInsumo;
import BuenSabor.model.SucursalEmpresa;
import BuenSabor.model.SucursalInsumo;
import BuenSabor.repository.ArticuloInsumoRepository;
import BuenSabor.repository.sucursal.SucursalEmpresaRepository;
import BuenSabor.repository.sucursal.SucursalInsumoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalEmpresaService {

    private final SucursalEmpresaRepository repository;
    private final SucursalInsumoRepository sucursalInsumoRepository;
    private final ArticuloInsumoRepository articuloInsumoRepository;

    public SucursalEmpresaService (
            SucursalEmpresaRepository repository,
            SucursalInsumoRepository stockRepository,
            ArticuloInsumoRepository articuloInsumoRepository){
        this.repository = repository;
        this.sucursalInsumoRepository = stockRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
    }

    public SucursalEmpresa guardar(SucursalEmpresa sucursal) {
        return repository.save(sucursal);
    }

    public SucursalEmpresa getSucursal(Long id) { return repository.getReferenceById(id); }

    public List<SucursalInsumoDTO> getStock(Long id) { return repository.getStock(id); }

    public void addStock(Long id, SucursalInsumoDTO stock) {
        // 1. Buscar la sucursal
        SucursalEmpresa sucursal = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sucursal no encontrada, id: " + id + "."));

        // 2. Buscar el insumo
        ArticuloInsumo insumo = articuloInsumoRepository.findById(stock.getIdInsumo())
                .orElseThrow(()-> new RuntimeException("Articulo no encontrado, id: " + stock.getIdInsumo() + "."));

        // 3. Verificar si ya existe el stock para esa combinación
        Optional<SucursalInsumo> existente = sucursalInsumoRepository.findBySucursalAndInsumo(sucursal, insumo);

        SucursalInsumo entidad;

        if (existente.isPresent()) {
            // Actualiza los valores existentes
            entidad = existente.get();
        } else {
            // Crea uno nuevo
            entidad = new SucursalInsumo();
            entidad.setSucursal(sucursal);
            entidad.setInsumo(insumo);
        }

        // 4. Setear los valores desde el DTO
        entidad.setStockActual(stock.getCantidadActual());
        entidad.setStockMinimo(stock.getCantidadMinima());
        entidad.setStockMaximo(stock.getCantidadMaxima());

        // 5. Guardar
        sucursalInsumoRepository.save(entidad);

    }

}

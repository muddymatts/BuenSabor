package BuenSabor.service.initDB;

import BuenSabor.model.*;
import BuenSabor.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitArticulosService {

    private final UnidadMedidaRepository unidadMedidaRepository;
    private final CategoriaArticuloRepository categoriaArticuloRepository;
    private final ArticuloInsumoRepository articuloInsumoRepository;
    private final CategoriaArticuloManufacturadoRepository categoriaArticuloManufacturadoRepository;
    private final ArticuloManufacturadoRepository articuloManufacturadoRepository;
    private final ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository;

    public InitArticulosService(UnidadMedidaRepository unidadMedidaRepository,
                                CategoriaArticuloRepository categoriaArticuloRepository,
                                ArticuloInsumoRepository articuloInsumoRepository,
                                CategoriaArticuloManufacturadoRepository categoriaArticuloManufacturadoRepository,
                                ArticuloManufacturadoRepository articuloManufacturadoRepository,
                                ArticuloManufacturadoDetalleRepository articuloManufacturadoDetalleRepository) {
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.categoriaArticuloRepository = categoriaArticuloRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.categoriaArticuloManufacturadoRepository = categoriaArticuloManufacturadoRepository;
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
        this.articuloManufacturadoDetalleRepository = articuloManufacturadoDetalleRepository;
    }

    public void setupUnidadesMedida() {
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
    }

    public void setupCategoriaInsumos() {
        if (categoriaArticuloRepository.count() == 0) {
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

            categoriaArticuloRepository.saveAll(
                    List.of(alimentos, bebidas, gaseosas, agua, vegetales, lacteos, quesos)
            );
        }
    }

    public void setupArticulosInsumo() {
        if (articuloInsumoRepository.count() == 0) {
            List<CategoriaArticulo> categorias = categoriaArticuloRepository.findAll();
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
    }

    public void setupCategoriasManufacturados() {
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
    }

    public void setupArticulosManufacturados() {
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
    }

    public void setupArticuloManufacturadoDetalles() {
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
    }
}

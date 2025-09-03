package BuenSabor.config;

import BuenSabor.service.initDB.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DataInitializer {

    private final InitUbicacionesService initUbicacionesService;
    private final InitArticulosService initArticulosService;
    private final InitClientesService initClientesService;
    private final InitEmpresasService initEmpresasService;
    private final InitSucursalesService initSucursalesService;
    private final InitDomiciliosService initDomiciliosService;
    private final InitEmpleadosService initEmpleadosService;

    public DataInitializer(InitUbicacionesService initUbicacionesService,
                           InitArticulosService initArticulosService,
                           InitClientesService initClientesService,
                           InitEmpresasService initEmpresasService,
                           InitSucursalesService initSucursalesService,
                           InitDomiciliosService initDomiciliosService,
                           InitEmpleadosService initEmpleadosService) {
        this.initUbicacionesService = initUbicacionesService;
        this.initArticulosService = initArticulosService;
        this.initClientesService = initClientesService;
        this.initEmpresasService = initEmpresasService;
        this.initSucursalesService = initSucursalesService;
        this.initDomiciliosService = initDomiciliosService;
        this.initEmpleadosService = initEmpleadosService;
    }

    @Bean
    @Order(1)
    CommandLineRunner initUbicaciones() {
        return args -> {
            initUbicacionesService.setupPaises();
            initUbicacionesService.setupProvincias();
            initUbicacionesService.setupLocalidades();
        };
    }

    @Bean
    @Order(2)
<<<<<<< HEAD

=======
>>>>>>> 1267b9ebf18dd8899b2ecac6de70468cd65ead53
    CommandLineRunner initDomicilios() {
        return args -> this.initDomiciliosService.setupDomicilios();
    }

    @Bean
    @Order(3)
    CommandLineRunner initEmpresas() {
        return args -> this.initEmpresasService.setupEmpresas();
    }

    @Bean
    @Order(4)
    CommandLineRunner initSucursales() {
        return args -> this.initSucursalesService.setupSucursales();
    }

    @Bean
    @Order(5)
    CommandLineRunner crearEmpleados() {
        return args -> initEmpleadosService.setupEmpleados();
    }

    @Bean
    @Order(6)
    CommandLineRunner initClientes() {
        return args -> this.initClientesService.setupClientes();
    }

    @Bean
    @Order(7)
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
}
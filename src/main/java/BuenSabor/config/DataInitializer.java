package BuenSabor.config;

import BuenSabor.service.initDB.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    CommandLineRunner initUbicaciones() {
        return args -> {
            initUbicacionesService.setupPaises();
            initUbicacionesService.setupProvincias();
            initUbicacionesService.setupLocalidades();
        };
    }

    @Bean
    CommandLineRunner initDomicilios() {
        return args -> this.initDomiciliosService.setupDomicilios();
    }

    @Bean
    CommandLineRunner initEmpresas() {
        return args -> this.initEmpresasService.setupEmpresas();
    }

    @Bean
    CommandLineRunner initSucursales() {
        return args -> this.initSucursalesService.setupSucursales();
    }

    @Bean
    CommandLineRunner crearEmpleados() {
        return args -> initEmpleadosService.setupEmpleados();
    }

    @Bean
    CommandLineRunner initClientes() {
        return args -> this.initClientesService.setupClientes();
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
}
package BuenSabor.service.initDB;

import BuenSabor.enums.RolEnum;
import BuenSabor.model.Empleado;
import BuenSabor.model.Usuario;
import BuenSabor.repository.EmpleadoRepository;
import BuenSabor.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitEmpleadosService {

    private final UsuarioRepository userRepo;
    private final EmpleadoRepository empleadoRepo;
    private final PasswordEncoder passwordEncoder;

    public InitEmpleadosService(UsuarioRepository userRepo, EmpleadoRepository empleadoRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.empleadoRepo = empleadoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void setupEmpleados() {
        if (userRepo.findByUsername("admin").isEmpty()) {
            // Admin
            Empleado adminEmpleado = new Empleado();
            adminEmpleado.setNombre("Admin");
            adminEmpleado.setApellido("Admin");
            adminEmpleado.setTelefono("123456789");
            adminEmpleado.setEmail("admin@example.com");
            adminEmpleado.setRol(RolEnum.ADMIN);
            empleadoRepo.save(adminEmpleado);

            Usuario adminUser = new Usuario();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setEmpleado(adminEmpleado);
            adminUser.setEstaActivo(true);

            userRepo.save(adminUser);
        }

        if (userRepo.findByUsername("cocina").isEmpty()) {
            // Cocina
            Empleado cocinaEmpleado = new Empleado();
            cocinaEmpleado.setNombre("Cocina");
            cocinaEmpleado.setApellido("Cocina");
            cocinaEmpleado.setTelefono("987654321");
            cocinaEmpleado.setEmail("cocina@example.com");
            cocinaEmpleado.setRol(RolEnum.COCINA);
            empleadoRepo.save(cocinaEmpleado);

            Usuario cocinaUser = new Usuario();
            cocinaUser.setUsername("cocina");
            cocinaUser.setPassword(passwordEncoder.encode("cocina123"));
            cocinaUser.setEmpleado(cocinaEmpleado);
            cocinaUser.setEstaActivo(true);

            userRepo.save(cocinaUser);
        }

        if (userRepo.findByUsername("delivery").isEmpty()) {
            // Delivery
            Empleado empleadoDelivery = new Empleado();
            empleadoDelivery.setNombre("Delivery");
            empleadoDelivery.setApellido("Delivery");
            empleadoDelivery.setTelefono("987654321");
            empleadoDelivery.setEmail("delivery@example.com");
            empleadoDelivery.setRol(RolEnum.DELIVERY);
            empleadoRepo.save(empleadoDelivery);

            Usuario deliveryUser = new Usuario();
            deliveryUser.setUsername("delivery");
            deliveryUser.setPassword(passwordEncoder.encode("delivery123"));
            deliveryUser.setEmpleado(empleadoDelivery);
            deliveryUser.setEstaActivo(true);

            userRepo.save(deliveryUser);
        }
    }
}

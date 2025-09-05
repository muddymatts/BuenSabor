package BuenSabor.repository;

import BuenSabor.model.PedidoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoVentaRepository extends JpaRepository<PedidoVenta, Long> {

    List<PedidoVenta> findByClienteId(Long idCliente);

    List<PedidoVenta> findBySucursalEmpresaId(Long idSucursal);

    List<PedidoVenta> findByClienteIdAndSucursalEmpresaId(Long idCliente, Long idSucursal);
}

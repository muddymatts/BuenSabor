package BuenSabor.repository;

import BuenSabor.model.PedidoVenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoVentaRepository extends JpaRepository<PedidoVenta, Long> {

    Page<PedidoVenta> findByClienteId(Long idCliente, Pageable pageable);

    Page<PedidoVenta> findBySucursalEmpresaId(Long idSucursal, Pageable pageable);

    Page<PedidoVenta> findByClienteIdAndSucursalEmpresaId(Long idCliente, Long idSucursal, Pageable pageable);

    @Override
    @NonNull
    Page<PedidoVenta> findAll(@NonNull Pageable pageable);
}


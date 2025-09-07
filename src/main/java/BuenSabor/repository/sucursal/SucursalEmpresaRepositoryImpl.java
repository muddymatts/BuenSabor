package BuenSabor.repository.sucursal;

import BuenSabor.dto.sucursal.CantidadDisponibleDTO;
import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SucursalEmpresaRepositoryImpl implements SucursalEmpresaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SucursalInsumoDTO> getStock(Long SucursalId) {
        String sql = "SELECT " +
                "i.id, " +
                "i.denominacion, " +
                "si.stock_minimo, " +
                "si.stock_maximo, " +
                "si.stock_actual, " +
                "um.denominacion " +
                "from sucursal_insumo si inner join articulo_insumo i inner join unidad_medida um " +
                "where si.sucursal_id =" +
                SucursalId +
                " and si.insumo_id = i.id and i.unidad_medida_id = um.id";

        List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

        return results.stream()
                .map(row -> new SucursalInsumoDTO(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue(),
                        (String) row[5],
                        new ArrayList<>()
                ))
                .toList();
    }

    @Override
    public List<CantidadDisponibleDTO> getCantidadDisponible(Long sucursalId) {
        String sql = "SELECT " +
                "am.id, " +
                "COALESCE(MIN(TRUNCATE(si.stock_actual / amd.cantidad, 0)), 0) AS cantidad_disponible " +
                "FROM articulo_manufacturado am " +
                "JOIN articulo_manufacturado_detalle amd " +
                "ON amd.articulo_manufacturado_id = am.id " +
                "LEFT JOIN sucursal_insumo si " +
                "ON si.insumo_id = amd.articulo_insumo_id " +
                "AND si.sucursal_id = :sucursalId " +
                "GROUP BY am.id, am.denominacion";

        List<Object[]> results = entityManager
                .createNativeQuery(sql)
                .setParameter("sucursalId", sucursalId)
                .getResultList();

        return results.stream()
                .map(row -> new CantidadDisponibleDTO(
                        ((Number) row[0]).longValue(),
                        ((Number) row[1]).intValue()
                ))
                .toList();
    }

    @Override
    public List<CantidadDisponibleDTO> getCantidadDisponiblePromos(Long SucursalId) {

        String sql = """
                SELECT 
                    p.id AS id,
                    MIN(
                        TRUNCATE(COALESCE(si.stock_actual, 0) / (amd.cantidad * pd.cantidad), 0)
                    ) AS cantidadDisponible
                FROM promocion p
                JOIN promocion_detalle pd ON pd.promocion_id = p.id
                JOIN articulo_manufacturado am ON am.id = pd.articulo_manufacturado_id
                JOIN articulo_manufacturado_detalle amd ON amd.articulo_manufacturado_id = am.id
                LEFT JOIN sucursal_insumo si 
                    ON si.insumo_id = amd.articulo_insumo_id
                    AND si.sucursal_id = :sucursalId
                GROUP BY p.id
                """;

        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("sucursalId", SucursalId)
                .getResultList();

        return results.stream()
                .map(row -> {
                    CantidadDisponibleDTO dto = new CantidadDisponibleDTO(
                            ((Number) row[0]).longValue(),
                            ((Number) row[1]).intValue()
                    );
                    return dto;
                }).toList();
    }
}

package BuenSabor.repository.sucursal;

import BuenSabor.dto.sucursal.SucursalInsumoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

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

}

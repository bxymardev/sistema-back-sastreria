package com.example.sistema_inventario_back.repository.compra;

import com.example.sistema_inventario_back.dto.compra.CompraListarDTO;
import com.example.sistema_inventario_back.entity.compra.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Integer>, JpaSpecificationExecutor<Compra> {

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.compra.CompraListarDTO(
                c.idCompra,
                c.totalCompra,
                c.proveedor.nombreComercial,
                cd.materiaPrima.nombreMateriaPrima,
                cd.unidadMedida,
                cd.cantidadUnidadMedida,
                c.fechaCompra,
                SIZE(c.compraDetalles)
            )
            FROM Compra c
            JOIN c.compraDetalles cd
            ORDER BY c.fechaCompra DESC
            """)
    Page<CompraListarDTO> findAllComprasPagination(Pageable pageable);

    // Total de compras
    @Query("SELECT COUNT(c) FROM Compra c")
    Long countTotalCompras();

    // Compras del mes actual
    @Query("SELECT COUNT(c) FROM Compra c WHERE c.fechaCompra >= :inicioMes")
    Long countComprasDelMes(@Param("inicioMes") LocalDateTime inicioMes);

    // Gasto del mes actual
    @Query("SELECT COALESCE(SUM(c.totalCompra), 0) FROM Compra c WHERE c.fechaCompra >= :inicioMes")
    BigDecimal sumGastoDelMes(@Param("inicioMes") LocalDateTime inicioMes);

    // Compras del mes anterior
    @Query("SELECT COUNT(c) FROM Compra c WHERE c.fechaCompra >= :inicioMesAnterior AND c.fechaCompra < :inicioMesActual")
    Long countComprasMesAnterior(@Param("inicioMesAnterior") LocalDateTime inicioMesAnterior,
                                 @Param("inicioMesActual") LocalDateTime inicioMesActual);

    // Gasto del mes anterior
    @Query("SELECT COALESCE(SUM(c.totalCompra), 0) FROM Compra c WHERE c.fechaCompra >= :inicioMesAnterior AND c.fechaCompra < :inicioMesActual")
    BigDecimal sumGastoMesAnterior(@Param("inicioMesAnterior") LocalDateTime inicioMesAnterior,
                                   @Param("inicioMesActual") LocalDateTime inicioMesActual);

    // Proveedor con más compras
    @Query("""
        SELECT c.proveedor.nombreComercial, COUNT(c)
        FROM Compra c
        GROUP BY c.proveedor.idProveedor, c.proveedor.nombreComercial
        ORDER BY COUNT(c) DESC
        """)
    List<Object[]> findProveedorTop();

    // Buscar la compra por el id con todas las relaciones cargadas
    @Query("""
    SELECT DISTINCT c FROM Compra c
    LEFT JOIN FETCH c.proveedor p
    LEFT JOIN FETCH c.usuario u
    LEFT JOIN FETCH c.compraDetalles cd
    LEFT JOIN FETCH cd.materiaPrima mp
    LEFT JOIN FETCH mp.imagenMateriaPrima img
    LEFT JOIN FETCH mp.tela t
    LEFT JOIN FETCH t.categoriaTela ct
    WHERE c.idCompra = :idCompra
    """)
    Optional<Compra> findByIdWithDetails(@Param("idCompra") Integer idCompra);
}
package com.example.sistema_inventario_back.repository.proveedor;

import com.example.sistema_inventario_back.dto.materia_prima.ProveedorActivoDTO;
import com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorListarDTO;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>, JpaSpecificationExecutor<Proveedor> {

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorListarDTO(
                p.idProveedor,
                p.nombresEncargado,
                p.nombreComercial,
                p.identificacionFiscal,
                p.direccion,
                p.fechaCreacion,
                p.numeroUno,
                p.numeroDos,
                p.tipoProveedor,
                p.estado,
                COALESCE(SUM(c.totalCompra), 0.00),
                MAX(c.fechaCompra)
            )
            FROM Proveedor p
            LEFT JOIN Compra c ON c.proveedor.idProveedor = p.idProveedor
            WHERE (:estado IS NULL OR p.estado = :estado)
            GROUP BY
                p.idProveedor,
                p.nombresEncargado,
                p.nombreComercial,
                p.identificacionFiscal,
                p.direccion,
                p.fechaCreacion,
                p.numeroUno,
                p.numeroDos,
                p.tipoProveedor,
                p.estado
    """)
    Page<ProveedorListarDTO> findAllResumen(@Param("estado")Boolean estado, Pageable pageable);

    @Query("""
    SELECT new com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorListarDTO(
        p.idProveedor,
        p.nombresEncargado,
        p.nombreComercial,
        p.identificacionFiscal,
        p.direccion,
        p.fechaCreacion,
        p.numeroUno,
        p.numeroDos,
        p.tipoProveedor,
        p.estado,
        COALESCE(SUM(c.totalCompra), 0.00),
        MAX(c.fechaCompra)
    )
    FROM Proveedor p
    LEFT JOIN Compra c ON c.proveedor.idProveedor = p.idProveedor
    WHERE :searchTerm IS NULL OR (
        LOWER(p.nombresEncargado) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
        LOWER(p.nombreComercial) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
        LOWER(COALESCE(p.identificacionFiscal, '')) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
        LOWER(p.numeroUno) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
        LOWER(COALESCE(p.numeroDos, '')) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
    )
    GROUP BY
        p.idProveedor,
        p.nombresEncargado,
        p.nombreComercial,
        p.identificacionFiscal,
        p.direccion,
        p.fechaCreacion,
        p.numeroUno,
        p.numeroDos,
        p.tipoProveedor,
        p.estado
""")
    Page<ProveedorListarDTO> findAllProveedoresConFiltros(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @Query ("SELECT COUNT(p) FROM Proveedor p")
    Long contarTotal();

    @Query("SELECT COUNT(p) FROM Proveedor p WHERE p.estado = true")
    Long contarActivos();

    @Query("SELECT COUNT(p) FROM Proveedor p WHERE p.estado = false")
    Long contarInactivos();

    @Query("""
           SELECT COUNT(p)
           FROM Proveedor p
           WHERE p.fechaCreacion >= :desde
           """)
    Long contarNuevosTresMeses(java.time.LocalDateTime desde);

    @Query("SELECT new com.example.sistema_inventario_back.dto.materia_prima.ProveedorActivoDTO(p.idProveedor, p.nombreComercial) " +
            "FROM Proveedor p WHERE p.estado = true ORDER BY p.nombreComercial ASC")
    List<ProveedorActivoDTO> findAllByEstadoTrue();


    // Consulta SQL para listar a todos los proveedores sin paginación
    @Query("""     
        SELECT new com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorListarDTO(
            p.idProveedor,
            p.nombresEncargado,
            p.nombreComercial,
            p.identificacionFiscal,
            p.direccion,
            p.fechaCreacion,
            p.numeroUno,
            p.numeroDos,
            p.tipoProveedor,
            p.estado,
            COALESCE(SUM(c.totalCompra), 0.00),
            MAX(c.fechaCompra)
        )

        FROM Proveedor p
        LEFT JOIN Compra c ON c.proveedor.idProveedor = p.idProveedor
        WHERE (:estado IS NULL OR p.estado = :estado)
        GROUP BY
            p.idProveedor, p.nombresEncargado, p.nombreComercial,
            p.identificacionFiscal, p.direccion, p.fechaCreacion, p.numeroUno,
            p.numeroDos, p.tipoProveedor, p.estado
    """)
    List<ProveedorListarDTO> findAllResumenSinPaginacion(@Param("estado") Boolean estado);
}
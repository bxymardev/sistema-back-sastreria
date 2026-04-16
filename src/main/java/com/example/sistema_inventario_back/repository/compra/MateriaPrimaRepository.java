package com.example.sistema_inventario_back.repository.compra;

import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaActivoDTO;
import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaListarDTO;
import com.example.sistema_inventario_back.entity.compra.MateriaPrima;
import com.example.sistema_inventario_back.entity.compra.TipoMateriaPrima;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Integer>, JpaSpecificationExecutor<MateriaPrima> {

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaListarDTO(
                 m.idMateriaPrima,
                 m.nombreMateriaPrima,
                 m.stockActual,
                 m.stockMinimo,
                 m.estadoMateriaPrima,
                 m.tipoMateriaPrima,
                 m.fechaCreacion,
                 m.precioUnitarioActual
            )
            FROM MateriaPrima m
            """)
    Page<MateriaPrimaListarDTO> findAllMateriaPrima(Pageable pageable);

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaActivoDTO(
                 m.idMateriaPrima,
                 m.nombreMateriaPrima,
                 m.precioUnitarioActual
            )
            FROM MateriaPrima m
            WHERE m.estadoMateriaPrima = com.example.sistema_inventario_back.entity.compra.EstadoMateriaPrima.ACTIVO
            ORDER BY m.nombreMateriaPrima ASC
            """)
    List<MateriaPrimaActivoDTO> findAllByEstadoActivo();

    // MateriaPrimaRepository.java

    @Query("""
        SELECT new com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaListarDTO(
             m.idMateriaPrima,
             m.nombreMateriaPrima,
             m.stockActual,
             m.stockMinimo,
             m.estadoMateriaPrima,
             m.tipoMateriaPrima,
             m.fechaCreacion,
             m.precioUnitarioActual
        )
        FROM MateriaPrima m
        WHERE (:tipo IS NULL OR m.tipoMateriaPrima = :tipo)
        """)
    Page<MateriaPrimaListarDTO> findAllMateriaPrimaByTipo(
            @Param("tipo") TipoMateriaPrima tipo,
            Pageable pageable);
}
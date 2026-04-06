package com.example.sistema_inventario_back.repository.tela;

import com.example.sistema_inventario_back.dto.tela.CategoriaTelaListarActivoDTO;
import com.example.sistema_inventario_back.dto.tela.CategoriaTelaListarDTO;
import com.example.sistema_inventario_back.dto.tela.CategoriaTelaListarPageDTO;
import com.example.sistema_inventario_back.entity.tela.CategoriaTela;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaTelaRepository extends JpaRepository<CategoriaTela, Integer>, JpaSpecificationExecutor<CategoriaTela> {

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.tela.CategoriaTelaListarDTO(
                c.idCategoriaTela,
                c.codigoCategoria,
                c.composicion,
                c.titulo,
                c.peso,
                c.densidad,
                c.ligamento,
                c.acabado
            )
            FROM CategoriaTela c
            ORDER BY c.fechaRegistro DESC
            """)
    Page<CategoriaTelaListarDTO> listarCategoriasRepository(Pageable pageable);

    // Consulta para listar a todas las categorias
    @Query("""
            SELECT new  com.example.sistema_inventario_back.dto.tela.CategoriaTelaListarActivoDTO( 
                c.idCategoriaTela,
                c.codigoCategoria
            )
            FROM CategoriaTela c
            ORDER BY c.fechaRegistro DESC
            """)
    List<CategoriaTelaListarActivoDTO> findAllCategoriasActivas();

    // Consulta para verificar si el codigo de categoria de tela exista (creación)
    @Query("""
        SELECT COUNT(c) > 0
        FROM CategoriaTela c
        WHERE c.codigoCategoria = :codigoCategoriaTela      
    """)
    boolean verificarCodigoCategoriaTela(String codigoCategoriaTela);

    // Consulta para verificar si existe un codigo de categoria agregando id
    @Query("""
        SELECT COUNT(c) > 0
        FROM CategoriaTela c
        WHERE c.codigoCategoria = :codigoCategoriaTela
        AND c.idCategoriaTela <> :id
    """)
    boolean verificarCodigoCategoriaTelaById(String codigoCategoriaTela, Integer id);

    // Contar total de categorías
    @Query("""
        SELECT COUNT(c)
        FROM CategoriaTela c
    """)
    Integer countTotalCategorias();

    // Obtener la categoría con más rotación (más telas asociadas)
    @Query("""
        SELECT c.titulo
        FROM CategoriaTela c
        LEFT JOIN c.listaTelas t
        GROUP BY c.idCategoriaTela, c.titulo
        ORDER BY COUNT(t) DESC
        LIMIT 1
    """)
    Optional<String> findCategoriaMasRotacion();

    // Obtener la última categoría actualizada
    @Query("""
        SELECT c.titulo
        FROM CategoriaTela c
        ORDER BY c.fechaRegistro DESC
        LIMIT 1
    """)
    Optional<String> findUltimaCategoriaActualizada();

    // Obtener la categoría obsoleta (sin telas asociadas y más antigua)
    @Query("""
        SELECT c.titulo
        FROM CategoriaTela c
        LEFT JOIN c.listaTelas t
        WHERE t IS NULL
        ORDER BY c.fechaRegistro ASC
        LIMIT 1
    """)
    Optional<String> findCategoriaObsoleta();
}
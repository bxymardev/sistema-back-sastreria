package com.example.sistema_inventario_back.repository.tela;

import com.example.sistema_inventario_back.dto.tela.TelaListarDTO;
import com.example.sistema_inventario_back.entity.tela.Tela;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TelaRepository extends JpaRepository<Tela, Integer>, JpaSpecificationExecutor<Tela> {

    // Consulta para listar telas
    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.tela.TelaListarDTO(
                c.idTela,
                c.color,
                c.codigoTela,
                c.categoriaTela.codigoCategoria
            )
            FROM Tela c
            ORDER BY c.fechaRegistro DESC
            """)
    Page<TelaListarDTO> listarTelasRepository(Pageable pageable);

    // Consulta para listar telas por categoria
    @Query("SELECT t FROM Tela t WHERE t.categoriaTela.idCategoriaTela = :idCategoriaTela")
    Page<Tela> findByCategoria(@Param("idCategoriaTela") Integer idCategoriaTela, Pageable pageable);
}
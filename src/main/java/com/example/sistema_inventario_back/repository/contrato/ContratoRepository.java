package com.example.sistema_inventario_back.repository.contrato;

import com.example.sistema_inventario_back.entity.contrato.Contrato;
import com.example.sistema_inventario_back.entity.contrato.EstadoContrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer>, JpaSpecificationExecutor<Contrato> {

    // Verificar si ya existe un código de contrato
    boolean existsByCodigoContrato(String codigoContrato);

    // Listar todos los contratos paginados (no cancelados prioritariamente)
    @Query("SELECT c FROM Contrato c ORDER BY c.fechaCreacion DESC")
    Page<Contrato> listarTodosLosContratos(Pageable pageable);

    // Listar contratos activos (excluyendo cancelados)
    @Query("SELECT c FROM Contrato c WHERE c.estadoContrato <> 'CANCELADO' ORDER BY c.fechaCreacion DESC")
    Page<Contrato> listarContratosActivos(Pageable pageable);

    // Listar contratos por estado específico
    Page<Contrato> findByEstadoContrato(EstadoContrato estadoContrato, Pageable pageable);

    // Obtener todos los contratos de un cliente específico
    @Query("SELECT c FROM Contrato c WHERE c.cliente.idCliente = :idCliente ORDER BY c.fechaCreacion DESC")
    List<Contrato> findByClienteId(@Param("idCliente") Integer idCliente);

    // Buscar contrato por código único
    Optional<Contrato> findByCodigoContrato(String codigoContrato);

    // Contar contratos activos de un cliente (para validaciones)
    @Query("SELECT COUNT(c) FROM Contrato c WHERE c.cliente.idCliente = :idCliente AND c.estadoContrato = 'ACTIVO'")
    long countContratosActivosByCliente(@Param("idCliente") Integer idCliente);
}

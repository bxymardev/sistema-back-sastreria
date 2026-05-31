package com.example.sistema_inventario_back.repository.contrato;

import com.example.sistema_inventario_back.entity.contrato.ContratoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoDetalleRepository extends JpaRepository<ContratoDetalle, Integer> {

    // Obtener todos los detalles (camisas) de un contrato específico
    List<ContratoDetalle> findByContrato_IdContrato(Integer idContrato);
}

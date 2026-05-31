package com.example.sistema_inventario_back.repository.contrato;

import com.example.sistema_inventario_back.entity.contrato.PagoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoContratoRepository extends JpaRepository<PagoContrato, Integer> {

    // Obtener todos los pagos de un contrato específico
    List<PagoContrato> findByContrato_IdContrato(Integer idContrato);

    // Sumar el total pagado de un contrato
    @Query("SELECT COALESCE(SUM(p.montoTotal), 0) FROM PagoContrato p WHERE p.contrato.idContrato = :idContrato")
    Double sumPagosByContrato(@Param("idContrato") Integer idContrato);
}

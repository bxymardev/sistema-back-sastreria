package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoContrato;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO de resumen financiero de un contrato.
 */
@Data
public class ContratoResumenDTO {
    private Integer idContrato;
    private String codigoContrato;
    private EstadoContrato estadoContrato;
    private BigDecimal montoTotal;
    private BigDecimal montoAdelantado;
    private BigDecimal totalPagado;
    private BigDecimal saldoPendiente;
    private int totalPrendas;
    private int prendasListas;
    private int prendasEntregadas;
    private boolean contratoCompletado;
}

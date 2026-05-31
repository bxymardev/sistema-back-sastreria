package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoPagoContrato;
import com.example.sistema_inventario_back.entity.contrato.MetodoPago;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de respuesta para pagos de un contrato.
 */
@Data
public class PagoContratoResponseDTO {
    private Integer idPagoContrato;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private EstadoPagoContrato estadoPagoContrato;
    private LocalDate fechaPago;
    private String observaciones;
    private Integer idContrato;
    private String codigoContrato;
}

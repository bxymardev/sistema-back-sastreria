package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoContrato;
import com.example.sistema_inventario_back.entity.contrato.TipoContrato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO liviano para listar contratos en paginación.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContratoListarDTO {
    private Integer idContrato;
    private String codigoContrato;
    private String clienteNombreCompleto;
    private BigDecimal montoTotal;
    private BigDecimal saldo;
    private EstadoContrato estadoContrato;
    private TipoContrato tipoContrato;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaCreacion;
}
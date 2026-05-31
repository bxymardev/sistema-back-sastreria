package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoContrato;
import com.example.sistema_inventario_back.entity.contrato.TipoContrato;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContratoResponseDTO {
    private Integer idContrato;
    private String codigoContrato;
    private BigDecimal montoTotal;
    private BigDecimal montoAdelantado;
    private BigDecimal saldo;
    private EstadoContrato estadoContrato;
    private TipoContrato tipoContrato;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String descripcion;
    private String motivoCancelacion;
    private LocalDateTime fechaCancelacion;

    // Datos del cliente asociado
    private Integer idCliente;
    private String clienteNombresCompleto;
    private String clienteTelefonoUno;

    // Detalles (camisas)
    private List<ContratoDetalleResponseDTO> detalles;
}

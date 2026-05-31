package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.MetodoPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para registrar un nuevo pago sobre un contrato.
 */
@Data
public class PagoContratoRequestDTO {

    @NotNull(message = "El monto del pago es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    private String observaciones;
}

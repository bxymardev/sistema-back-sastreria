package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.TipoContrato;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ContratoRequestDTO {

    @NotNull(message = "El id del cliente es obligatorio")
    private Integer idCliente;

    /** Si se omite, el sistema lo genera automáticamente */
    @Size(max = 30, message = "El código de contrato no debe exceder 30 caracteres")
    private String codigoContrato;

    @NotNull(message = "El monto total es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto total debe ser mayor a 0")
    private BigDecimal montoTotal;

    @NotNull(message = "El monto adelantado es obligatorio")
    @DecimalMin(value = "0.00", inclusive = true, message = "El monto adelantado no puede ser negativo")
    private BigDecimal montoAdelantado;

    @NotNull(message = "El tipo de contrato es obligatorio")
    private TipoContrato tipoContrato;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de entrega es obligatoria")
    private LocalDate fechaFin;

    @Size(max = 500, message = "La descripción no debe exceder 500 caracteres")
    private String descripcion;

    @NotEmpty(message = "El contrato debe tener al menos un ítem (camisa)")
    @Valid
    private List<ContratoDetalleRequestDTO> detalles;
}

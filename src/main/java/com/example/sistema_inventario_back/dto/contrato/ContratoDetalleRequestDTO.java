package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoProducto;
import com.example.sistema_inventario_back.entity.contrato.Talla;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContratoDetalleRequestDTO {

    @NotBlank(message = "La descripción del producto es obligatoria")
    @Size(max = 150, message = "La descripción no debe exceder 150 caracteres")
    private String descripcionProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio unitario debe ser mayor a 0")
    private BigDecimal precioUnitario;

    private Talla talla;

    @Size(max = 50, message = "El color no debe exceder 50 caracteres")
    private String color;

    @Size(max = 500, message = "Las observaciones no deben exceder 500 caracteres")
    private String observaciones;

    private EstadoProducto estadoProducto;
}

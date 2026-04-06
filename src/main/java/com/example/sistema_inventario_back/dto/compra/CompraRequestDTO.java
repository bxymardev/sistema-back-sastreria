package com.example.sistema_inventario_back.dto.compra;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompraRequestDTO {
    @NotBlank(message = "El número de comprobante es obligatorio")
    @Size(max = 50, message = "El número de comprobante no puede exceder 50 caracteres")
    private String numeroComprobante;

    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    private BigDecimal descuento;

    @Size(max = 255, message = "La nota no puede exceder 255 caracteres")
    private String notaEdicion;

    @NotNull(message = "El proveedor es obligatorio")
    private Integer idProveedor;

    @NotEmpty(message = "Debe incluir al menos un detalle de compra")
    @Valid
    private List<CompraDetalleRequestDTO> detalles;
}
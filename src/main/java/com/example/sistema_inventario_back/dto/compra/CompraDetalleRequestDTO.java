package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadCompra;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompraDetalleRequestDTO {

    @NotNull(message = "La materia prima es obligatoria")
    private Integer idMateriaPrima;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    private Double cantidadUnidadCompra;

    @NotNull(message = "La cantidad en unidad de medida es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
    private Double cantidadUnidadMedida;

    @NotNull(message = "La unidad de compra es obligatoria")
    private UnidadCompra unidadCompra;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UnidadMedida unidadMedida;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precioUnitario;
}
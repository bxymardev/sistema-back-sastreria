package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoProducto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO para actualizar el estado de fabricación de un ítem (camisa) del contrato.
 */
@Data
public class ContratoEstadoProductoDTO {

    @NotNull(message = "El nuevo estado del producto es obligatorio")
    private EstadoProducto estadoProducto;
}

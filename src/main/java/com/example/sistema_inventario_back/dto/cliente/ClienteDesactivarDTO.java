package com.example.sistema_inventario_back.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para desactivar (eliminación lógica) un cliente.
 */
@Data
public class ClienteDesactivarDTO {

    @NotBlank(message = "El motivo de desactivación es obligatorio")
    @Size(max = 300, message = "El motivo no debe exceder 300 caracteres")
    private String motivo;
}

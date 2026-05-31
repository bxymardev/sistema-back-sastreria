package com.example.sistema_inventario_back.dto.contrato;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO para cancelar (eliminación lógica) un contrato.
 */
@Data
public class ContratoCancelarDTO {

    @NotBlank(message = "El motivo de cancelación es obligatorio")
    @Size(max = 300, message = "El motivo no debe exceder 300 caracteres")
    private String motivo;
}

package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoContrato;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO para editar únicamente los campos NO sensibles de un contrato.
 * Los campos sensibles (monto, cliente, código) no se modifican aquí.
 */
@Data
public class ContratoEditarDTO {

    @Future(message = "La fecha de entrega debe ser una fecha futura")
    private LocalDate fechaFin;

    @Size(max = 500, message = "La descripción no debe exceder 500 caracteres")
    private String descripcion;

    private EstadoContrato estadoContrato;
}

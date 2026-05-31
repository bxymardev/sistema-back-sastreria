package com.example.sistema_inventario_back.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    private String nombres;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 50, message = "El apellido paterno no debe exceder 50 caracteres")
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(max = 50, message = "El apellido materno no debe exceder 50 caracteres")
    private String apellidoMaterno;

    @Size(max = 20, message = "El carnet de identidad no debe exceder más de 20 caracteres")
    private String carnetIdentidad;

    @NotBlank(message = "El primer teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono principal no debe exceder 15 caracteres")
    private String telefonoUno;

    @Size(max = 15, message = "El teléfono alternativo no debe exceder 15 caracteres")
    private String telefonoDos;
}
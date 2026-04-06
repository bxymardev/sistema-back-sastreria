package com.example.sistema_inventario_back.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioUpdatePasswordDTO {

    @NotBlank(message = "La contraseña actual es obligatoria")
    private String actualPassword;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    private String nuevoPassword;
}
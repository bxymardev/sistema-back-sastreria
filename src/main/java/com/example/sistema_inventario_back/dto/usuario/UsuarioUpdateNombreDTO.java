package com.example.sistema_inventario_back.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioUpdateNombreDTO {
    @NotBlank(message = "El nuevo nombre de usuario es obligatorio")
    private String nuevoNombreUsuario;
}
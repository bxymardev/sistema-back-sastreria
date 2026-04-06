package com.example.sistema_inventario_back.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtActualizacionResponse {
    private String token;
    private String nombreUsuario;
}
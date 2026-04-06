package com.example.sistema_inventario_back.dto.usuario;

import com.example.sistema_inventario_back.entity.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private Usuario usuario;
}
package com.example.sistema_inventario_back.entity.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Boolean estado;
    private String nombre_usuario;
    private String carnetIdentidad;
    private String password;
}
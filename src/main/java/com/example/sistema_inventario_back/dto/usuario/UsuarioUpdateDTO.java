package com.example.sistema_inventario_back.dto.usuario;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String carnetIdentidad;
}
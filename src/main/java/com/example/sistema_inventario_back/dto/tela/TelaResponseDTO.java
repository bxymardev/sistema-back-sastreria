package com.example.sistema_inventario_back.dto.tela;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TelaResponseDTO {
    private Integer idTela;
    private String color;
    private String codigoTela;
    private LocalDateTime fechaRegistro;
    private Integer idCategoriaTela;
    private Integer idImagenTela;
    private String imagenUrl;
}
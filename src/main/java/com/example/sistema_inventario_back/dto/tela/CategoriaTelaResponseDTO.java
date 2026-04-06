package com.example.sistema_inventario_back.dto.tela;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoriaTelaResponseDTO {
    private Integer idCategoriaTela;
    private String codigoCategoria;
    private String titulo;
    private String composicion;
    private String peso;
    private String ancho;
    private String densidad;
    private String ligamento;
    private String acabado;
    private LocalDateTime fechaRegistro;
}
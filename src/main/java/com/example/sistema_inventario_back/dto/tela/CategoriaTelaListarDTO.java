package com.example.sistema_inventario_back.dto.tela;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTelaListarDTO {
    private Integer idCategoriaTela;
    private String codigoCategoria;
    private String composicion;
    private String titulo;
    private String peso;
    private String densidad;
    private String ligamento;
    private String acabado;
}
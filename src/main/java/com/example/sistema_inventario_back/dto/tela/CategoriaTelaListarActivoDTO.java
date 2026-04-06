package com.example.sistema_inventario_back.dto.tela;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaTelaListarActivoDTO {
    private Integer idCategoriaTela;
    private String codigoCategoria;
}
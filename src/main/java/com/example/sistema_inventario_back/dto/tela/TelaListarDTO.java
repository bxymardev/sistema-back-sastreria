package com.example.sistema_inventario_back.dto.tela;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelaListarDTO {
    private Integer idTela;
    private String color;
    private String codigoTela;
    private String codigoCategoriaTela;
}
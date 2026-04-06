package com.example.sistema_inventario_back.dto.tela;

import lombok.Data;

@Data
public class TelaUpdateDTO {
    private Integer idTela;
    private String color;
    private String codigoTela;
    private Integer idCategoriaTela;
    private String motivoUpdate;
}
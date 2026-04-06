package com.example.sistema_inventario_back.dto.tela;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelaImagenDTO {
    private Integer idTela;
    private String imagenUrl;
}
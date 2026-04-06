package com.example.sistema_inventario_back.dto.tela;

import jakarta.validation.constraints.NotBlank;

public record CategoriaTelaActualizarCodigoDTO(
        @NotBlank(message = "El codigo de la categoria de la tela no puede estar vació")
        String codigoCategoriaTela
) {}
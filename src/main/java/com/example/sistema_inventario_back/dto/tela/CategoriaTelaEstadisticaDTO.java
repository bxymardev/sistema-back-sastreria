package com.example.sistema_inventario_back.dto.tela;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoriaTelaEstadisticaDTO(
        @JsonProperty("totalCategorias")
        Integer totalCategorias,

        @JsonProperty("categoriaMasRotacion")
        String categoriaMasRotacion,

        @JsonProperty("ultimaCategoriaActualizada")
        String ultimaCategoriaActualizada,

        @JsonProperty("categoriaObsoleta")
        String categoriaObsoleta
) {}
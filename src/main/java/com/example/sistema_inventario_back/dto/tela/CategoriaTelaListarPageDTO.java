package com.example.sistema_inventario_back.dto.tela;

import lombok.Data;

import java.util.List;

@Data
public class CategoriaTelaListarPageDTO {
    private List<CategoriaTelaListarDTO> listaCategorias;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}
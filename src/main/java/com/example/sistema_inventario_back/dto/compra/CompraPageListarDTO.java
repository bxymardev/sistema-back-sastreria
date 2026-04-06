package com.example.sistema_inventario_back.dto.compra;

import lombok.Data;

import java.util.List;

@Data
public class CompraPageListarDTO {
    private List<CompraListarDTO> listaCompra;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}
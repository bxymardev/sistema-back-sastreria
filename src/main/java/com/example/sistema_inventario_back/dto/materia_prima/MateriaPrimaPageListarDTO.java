package com.example.sistema_inventario_back.dto.materia_prima;

import lombok.Data;

import java.util.List;

@Data
public class MateriaPrimaPageListarDTO {
    private List<MateriaPrimaListarDTO> materiaPrima;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}
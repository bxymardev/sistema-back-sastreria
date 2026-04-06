package com.example.sistema_inventario_back.dto.proveedor.representante;

import lombok.Data;

import java.util.List;

@Data
public class RepresentantePageResponseDTO {
    private List<RepresentanteResponseDTO> representantes;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}
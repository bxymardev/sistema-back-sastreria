package com.example.sistema_inventario_back.dto.contrato;

import lombok.Data;

import java.util.List;

/**
 * DTO para respuesta paginada de contratos.
 */
@Data
public class ContratoListarPageDTO {
    private List<ContratoListarDTO> contratos;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}

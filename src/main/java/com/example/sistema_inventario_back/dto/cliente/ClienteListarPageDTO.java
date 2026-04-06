package com.example.sistema_inventario_back.dto.cliente;

import lombok.Data;

import java.util.List;

@Data
public class ClienteListarPageDTO {
    private List<ClienteListarDTO> clientes;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}
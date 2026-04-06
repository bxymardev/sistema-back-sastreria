package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import lombok.Data;

import java.util.List;

@Data
public class ProveedorPageResponseDTO {
    private List<ProveedorResponseDTO> proveedores;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}
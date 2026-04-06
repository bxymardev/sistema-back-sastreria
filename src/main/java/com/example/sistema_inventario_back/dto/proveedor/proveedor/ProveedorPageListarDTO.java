package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import lombok.Data;

import java.util.List;

@Data
public class ProveedorPageListarDTO {
    private List<ProveedorListarDTO> proveedores;
    private int paginaActual;
    private int totalPaginas;
    private long totalElementos;
    private int tamanioPagina;
}
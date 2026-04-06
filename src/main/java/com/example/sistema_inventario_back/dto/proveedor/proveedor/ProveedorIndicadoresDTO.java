package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import lombok.Data;

@Data
public class ProveedorIndicadoresDTO {
    private Long total;
    private Long activos;
    private Long inactivos;
    private Long nuevosTresMeses;
}
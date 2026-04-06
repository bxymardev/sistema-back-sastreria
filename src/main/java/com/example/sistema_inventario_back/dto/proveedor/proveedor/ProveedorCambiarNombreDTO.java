package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import lombok.Data;

@Data
public class ProveedorCambiarNombreDTO {
    private Integer idProveedor;
    private String nuevoNombre;
}
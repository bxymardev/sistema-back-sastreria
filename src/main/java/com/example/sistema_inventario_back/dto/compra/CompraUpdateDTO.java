package com.example.sistema_inventario_back.dto.compra;

import lombok.Data;

@Data
public class CompraUpdateDTO {
    private Integer idCompra;
    private Double totalCompra;
    private Integer idProveedor;
    private String notaEdicion;
    private CompraDetalleUpdateDTO detalle;
}
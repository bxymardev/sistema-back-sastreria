package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadCompra;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import lombok.Data;

@Data
public class CompraDetalleUpdateDTO {
    private Double cantidadUnidadCompra;
    private Double cantidadUnidadMedida;
    private UnidadCompra unidadCompra;
    private UnidadMedida unidadMedida;
}
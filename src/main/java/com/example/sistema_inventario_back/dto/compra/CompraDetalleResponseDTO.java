package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadCompra;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompraDetalleResponseDTO {

    private Integer idCompraDetalle;
    private Double cantidadUnidadCompra;
    private Double cantidadUnidadMedida;
    private UnidadCompra unidadCompra;
    private UnidadMedida unidadMedida;
    private BigDecimal precioUnitario;
    private BigDecimal subTotalDetalle;

    // Materia Prima
    private Integer idMateriaPrima;
    private String nombreMateriaPrima;
}
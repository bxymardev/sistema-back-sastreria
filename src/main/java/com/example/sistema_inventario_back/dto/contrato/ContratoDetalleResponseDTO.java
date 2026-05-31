package com.example.sistema_inventario_back.dto.contrato;

import com.example.sistema_inventario_back.entity.contrato.EstadoProducto;
import com.example.sistema_inventario_back.entity.contrato.Talla;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContratoDetalleResponseDTO {
    private Integer idContratoDetalle;
    private String descripcionProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subTotal;
    private Talla talla;
    private String color;
    private String observaciones;
    private EstadoProducto estadoProducto;
}

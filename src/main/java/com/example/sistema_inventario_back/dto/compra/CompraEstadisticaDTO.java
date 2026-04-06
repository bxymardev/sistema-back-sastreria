package com.example.sistema_inventario_back.dto.compra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraEstadisticaDTO {
    private Long totalCompras;
    private Long comprasEsteMes;
    private BigDecimal gastoEsteMes;
    private String proveedorTop;
    private Long comprasProveedorTop;

    private Long comprasMesAnterior;
    private BigDecimal gastoMesAnterior;
}
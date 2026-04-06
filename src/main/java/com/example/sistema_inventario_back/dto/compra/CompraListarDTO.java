package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraListarDTO {
    private Integer idCompra;
    private BigDecimal totalCompra;
    private String nombreComercial;
    private String nombreMateriaPrima;
    private UnidadMedida unidadMedida;
    private Double cantidadUnidadMedida;
    private LocalDateTime fechaCompra;
    private Integer cantidadItems;
}
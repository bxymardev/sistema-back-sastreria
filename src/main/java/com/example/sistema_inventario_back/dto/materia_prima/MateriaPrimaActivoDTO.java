package com.example.sistema_inventario_back.dto.materia_prima;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaPrimaActivoDTO {
    private Integer idMateriaPrima;
    private String nombreMateriaPrima;
    private BigDecimal precioUnitarioActual;
}
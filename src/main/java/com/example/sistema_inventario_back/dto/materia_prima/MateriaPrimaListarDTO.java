package com.example.sistema_inventario_back.dto.materia_prima;
import com.example.sistema_inventario_back.entity.compra.EstadoMateriaPrima;
import com.example.sistema_inventario_back.entity.compra.TipoMateriaPrima;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaPrimaListarDTO {
    private Integer idMateriaPrima;
    private String nombreMateriaPrima;
    private Double stockActual;
    private Double stockMinimo;
    private EstadoMateriaPrima estadoMateriaPrima;
    private TipoMateriaPrima tipoMateriaPrima;
    private LocalDateTime fechaCreacion;
    private BigDecimal precioUnitarioActual;
}
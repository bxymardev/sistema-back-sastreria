package com.example.sistema_inventario_back.dto.materia_prima;
import com.example.sistema_inventario_back.entity.compra.TipoMateriaPrima;
import com.example.sistema_inventario_back.entity.compra.UbicacionAlmacen;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MateriaPrimaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreMateriaPrima;

    // Opcional
    private String marca;

    // Opcional
    private String modelo;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Double stockActual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Double stockMinimo;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UnidadMedida unidadMedida;

    @NotNull(message = "El tipo de materia prima es obligatorio")
    private TipoMateriaPrima tipoMateriaPrima;

    @NotNull(message = "La ubicación es obligatoria")
    private UbicacionAlmacen ubicacionAlmacen;
}
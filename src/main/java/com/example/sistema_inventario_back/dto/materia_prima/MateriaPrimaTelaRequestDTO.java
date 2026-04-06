package com.example.sistema_inventario_back.dto.materia_prima;

import com.example.sistema_inventario_back.entity.compra.UbicacionAlmacen;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MateriaPrimaTelaRequestDTO {

    // Campos de MateriaPrima
    @NotBlank(message = "El nombre es obligatorio")
    private String nombreMateriaPrima;

    private String marca;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0)
    private Double stockActual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0)
    private Double stockMinimo;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UnidadMedida unidadMedida;

    @NotNull(message = "La ubicación es obligatoria")
    private UbicacionAlmacen ubicacionAlmacen;

    // Campos específicos de Tela
    @NotBlank(message = "El color es obligatorio")
    private String color;

    private String codigoTela;

    @NotNull(message = "La categoría de tela es obligatoria")
    private Integer idCategoriaTela;
}
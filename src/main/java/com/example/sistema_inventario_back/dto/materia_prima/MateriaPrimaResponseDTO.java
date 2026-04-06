package com.example.sistema_inventario_back.dto.materia_prima;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MateriaPrimaResponseDTO {
    private Integer idMateriaPrima;
    private String nombreMateriaPrima;
    private String marca;
    private String modelo;
    private Double stockActual;
    private Double stockMinimo;
    private String unidadMedida;
    private String tipoMateriaPrima;
    private String ubicacionAlmacen;
    private String estadoMateriaPrima;
    private BigDecimal precioUnitarioActual;
    private String usuario;
}
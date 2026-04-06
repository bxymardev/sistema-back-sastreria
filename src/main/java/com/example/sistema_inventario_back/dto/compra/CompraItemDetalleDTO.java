package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraItemDetalleDTO {

    // IDENTIFICADOR DEL DETALLE
    private Integer idDetalleCompra;
    private Double cantidadUnidadCompra;
    private Double cantidadUnidadMedida;
    private String unidadCompra;
    private String unidadMedida;
    private BigDecimal precioUnitario;
    private BigDecimal subTotalDetalle;

    // INFORMACION DE LA MATERIA PRIMA
    private Integer idMateriaPrima;
    private String nombreMateriaPrima;
    private String marca;
    private String modelo;
    private String tipoMateriaPrima;
    private String ubicacionAlmacen;

    // IMAGEN DE LA MATERIA PRIMA
    private String imagenUrl;
    private String nombreImagen;

    private Boolean esTela;

    // ENTIDAD TELA
    private String color;
    private String codigoTela;

    // CATEGORIA TELA
    private String codigoCategoria;
    private String composicion;
    private String tituloCategoria;
    private String peso;
    private String ancho;
    private String densidad;
    private String ligamento;
    private String acabado;
}
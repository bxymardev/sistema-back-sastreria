package com.example.sistema_inventario_back.entity.contrato;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Representa cada ítem (camisa) dentro de un Contrato.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContratoDetalle;

    /** Nombre o tipo de prenda, ej: "Camisa Formal Manga Larga" */
    @Column(nullable = false, length = 150)
    private String descripcionProducto;

    /** Número de prendas de este ítem */
    @Column(nullable = false)
    private Integer cantidad;

    /** Precio unitario por prenda */
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    /** Subtotal = cantidad * precioUnitario (calculado automáticamente) */
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subTotal;

    /** Talla de la prenda */
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Talla talla;

    /** Color de la prenda */
    @Column(nullable = true, length = 50)
    private String color;

    /**
     * Observaciones adicionales: medidas personalizadas, tipo de tela,
     * estilo de cuello, tipo de manga, etc.
     */
    @Column(nullable = true, length = 500)
    private String observaciones;

    /** Estado de fabricación de esta prenda */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProducto estadoProducto = EstadoProducto.PENDIENTE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_contrato", nullable = false)
    private Contrato contrato;

    @PrePersist
    @PreUpdate
    public void calcularSubTotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subTotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
        if (estadoProducto == null) {
            estadoProducto = EstadoProducto.PENDIENTE;
        }
    }
}
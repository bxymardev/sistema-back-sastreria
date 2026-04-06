    package com.example.sistema_inventario_back.entity.compra;

    import com.example.sistema_inventario_back.entity.proveedor.UnidadCompra;
    import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
    import jakarta.persistence.*;
    import lombok.*;

    import java.math.BigDecimal;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CompraDetalle {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idCompraDetalle;

        @Column(nullable = false)
        private Double cantidadUnidadCompra;

        @Column(nullable = false)
        private Double cantidadUnidadMedida;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private UnidadCompra unidadCompra;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private UnidadMedida unidadMedida;

        @Column(precision = 10, scale = 2, nullable = false)
        private BigDecimal precioUnitario;

        @Column(precision = 10, scale = 2, nullable = false)
        private BigDecimal subTotalDetalle;

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_compra", nullable = false)
        private Compra compra;

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_materia_prima", nullable = false)
        private MateriaPrima materiaPrima;

        @PrePersist
        @PreUpdate
        public void calcularSubTotal() {
            if (cantidadUnidadCompra != null && precioUnitario != null) {
                this.subTotalDetalle = precioUnitario.multiply(BigDecimal.valueOf(cantidadUnidadCompra));
            }
        }
    }
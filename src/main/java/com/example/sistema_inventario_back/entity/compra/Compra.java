package com.example.sistema_inventario_back.entity.compra;

import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompra;

    @Column(unique = true)
    private String numeroComprobante;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal descuento;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totalCompra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCompra estadoCompra;

    private String notaEdicion;

    @Column(nullable = false)
    private LocalDateTime fechaCompra;

    private LocalDateTime fechaActualizacion;

    // Proveedor
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    // Compra Detalle (Multiples detalles)
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompraDetalle> compraDetalles = new ArrayList<>();

    // Usuario
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        fechaCompra = LocalDateTime.now();
        if (estadoCompra == null){
            estadoCompra = EstadoCompra.COMPLETADA;
        }
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    // Método helper para agregar detalles
    public void addDetalle(CompraDetalle detalle) {
        compraDetalles.add(detalle);
        detalle.setCompra(this);
    }

    public void removeDetalles(CompraDetalle detalle) {
        compraDetalles.remove(detalle);
        detalle.setCompra(null);
    }
}
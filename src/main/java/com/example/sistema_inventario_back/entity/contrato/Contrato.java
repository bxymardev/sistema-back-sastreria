package com.example.sistema_inventario_back.entity.contrato;

import com.example.sistema_inventario_back.entity.cliente.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContrato;

    @Column(nullable = false, unique = true, length = 30)
    private String codigoContrato;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal montoTotal;

    /**
     * Monto adelantado (anticipo) pagado al inicio del contrato
     * para comenzar la elaboración de las camisas.
     */
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal montoAdelantado;

    /**
     * Saldo pendiente = montoTotal - montoAdelantado.
     * Se actualiza con cada pago registrado.
     */
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoContrato estadoContrato;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoContrato tipoContrato;

    /** Fecha de inicio / formalización del contrato */
    @Column(nullable = false)
    private LocalDate fechaInicio;

    /** Fecha límite de entrega acordada con el cliente */
    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = true)
    private LocalDateTime fechaActualizacion;

    /** Descripción general, observaciones o medidas del pedido */
    @Column(nullable = true, length = 500)
    private String descripcion;

    /** Motivo de cancelación (si estadoContrato = CANCELADO) */
    @Column(nullable = true, length = 300)
    private String motivoCancelacion;

    /** Fecha y hora en que se canceló el contrato */
    @Column(nullable = true)
    private java.time.LocalDateTime fechaCancelacion;

    // Relacion con Cliente (muchos contratos pertenecen a un cliente)
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContratoDetalle> contratoDetalles = new ArrayList<>();

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PagoContrato> pagoContratos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        fechaCreacion = fechaActualizacion = LocalDateTime.now();
        if (estadoContrato == null) {
            estadoContrato = EstadoContrato.ACTIVO;
        }
        // Calcular saldo inicial
        if (montoTotal != null && montoAdelantado != null) {
            saldo = montoTotal.subtract(montoAdelantado);
        }
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = LocalDateTime.now();
        // Recalcular saldo en cada actualización
        if (montoTotal != null && montoAdelantado != null) {
            saldo = montoTotal.subtract(montoAdelantado);
        }
    }

    /**
     * Cancela el contrato de forma lógica.
     * @param motivo razón de la cancelación
     */
    public void cancelar(String motivo) {
        this.estadoContrato = EstadoContrato.CANCELADO;
        this.motivoCancelacion = motivo;
        this.fechaCancelacion = java.time.LocalDateTime.now();
    }

    /** Helper para añadir un detalle manteniendo la bidireccionalidad */
    public void addDetalle(ContratoDetalle detalle) {
        contratoDetalles.add(detalle);
        detalle.setContrato(this);
    }

    /** Helper para remover un detalle */
    public void removeDetalle(ContratoDetalle detalle) {
        contratoDetalles.remove(detalle);
        detalle.setContrato(null);
    }
}
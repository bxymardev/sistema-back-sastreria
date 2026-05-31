package com.example.sistema_inventario_back.entity.contrato;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPagoContrato;

    @Column(nullable = false)
    private Double montoTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPagoContrato estadoPagoContrato;

    @Column(nullable = false)
    private LocalDate fechaPago;

    /** Observaciones opcionales sobre el pago */
    @Column(nullable = true, length = 300)
    private String observaciones;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_contrato", nullable = false)
    private Contrato contrato;

    @OneToMany(mappedBy = "pagoContrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PagoContratoDetalle> pagoContratoDetalles = new ArrayList<>();

    /**
     * Constructor de conveniencia para registrar un pago rápido.
     */
    public PagoContrato(Double montoTotal, MetodoPago metodoPago, EstadoPagoContrato estadoPagoContrato, LocalDate fechaPago) {
        this.montoTotal = montoTotal;
        this.metodoPago = metodoPago;
        this.estadoPagoContrato = estadoPagoContrato;
        this.fechaPago = fechaPago;
    }
}
package com.example.sistema_inventario_back.entity.contrato;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PagoContratoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPagoContratoDetalle;

    @Column(nullable = true)
    private String descripcion;

    @NonNull
    private double montoParcial;

    @NonNull
    private LocalDateTime fechaRegistro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pago_contrato_id")
    private PagoContrato pagoContrato;
}
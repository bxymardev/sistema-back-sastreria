package com.example.sistema_inventario_back.entity.contrato;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class PagoContrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPagoContrato;

    @NonNull
    private Double montoTotal;

    @NonNull
    private MetodoPago metodoPago;

    @NonNull
    private EstadoPagoContrato estadoPagoContrato;

    @NonNull
    private LocalDate fechaPago;

    @ManyToOne(optional = false)
    private Contrato contrato;

    @OneToMany(mappedBy = "pagoContrato", cascade = CascadeType.ALL)
    private List<PagoContratoDetalle> pagoContratoDetalles;
}
package com.example.sistema_inventario_back.entity.contrato;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContrato;

    @NonNull
    @Column(nullable = false, unique = true)
    private String codigoContrato;

    @NonNull
    private Double montoTotal;

    @NonNull
    private EstadoContrato estadoContrato;

    @NonNull
    private TipoContrato tipoContrato;

    @NonNull
    private LocalDate fechaInicion;

    @NonNull
    private LocalDate fechaFin;

    @NonNull
    private LocalDateTime fechaCreacion;

    @NonNull
    private String descripcion;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL)
    private List<ContratoDetalle> contratoDetalles;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL)
    private List<PagoContrato> pagoContratos;

    @PrePersist
    public void prePersist(){
        fechaCreacion = LocalDateTime.now();
    }
}
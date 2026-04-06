package com.example.sistema_inventario_back.entity.contrato;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ContratoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContratoDetalle;

    //Opcional (Si es producto)
    @Column(nullable = true)
    private Integer cantidad;

    //Opcional (Si es precio unitario)
    @Column(nullable = true)
    private Double precioUnitario;

    @NonNull
    private Double subTotal;

    @ManyToOne(optional = false)
    private Contrato contrato;
}
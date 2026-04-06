package com.example.sistema_inventario_back.entity.cliente;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @NonNull
    private String nombre;

    @NonNull
    private String apellidoPaterno;

    @NonNull
    private String apellidoMaterno;

    @Column(nullable = true)
    private String carnetIdentidad;

    @NonNull
    private String telefono1;

    @Column(nullable = true)
    private String telefono2;

    @Column(nullable = true)
    private LocalDateTime fechaRegistro;

    @NonNull
    private Boolean estadoCliente;

    @Column(nullable = true)
    private LocalDateTime fechaEliminacion;

    @NonNull
    private String motivoEliminacion;

    @PrePersist
    public void prePersist(){
        fechaRegistro = LocalDateTime.now();
    }

    public void desactivar(String motivo){
        this.estadoCliente = false;
        this.motivoEliminacion = motivo;
        this.fechaEliminacion = LocalDateTime.now();
    }
}
package com.example.sistema_inventario_back.entity.cliente;

import com.example.sistema_inventario_back.entity.contrato.Contrato;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(nullable = true, length = 20)
    private String carnetIdentidad;

    @Column(nullable = false, length = 15)
    private String telefonoUno;

    @Column(nullable = true, length = 15)
    private String telefonoDos;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = true)
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private Boolean estadoCliente = true;

    @Column(nullable = true)
    private LocalDateTime fechaEliminacion;

    @Column(nullable = true)
    private String motivoEliminacion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Contrato> contratos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        fechaRegistro = fechaActualizacion = LocalDateTime.now();
        if (estadoCliente == null) {
            estadoCliente = true;
        }
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public void desactivar(String motivo) {
        this.estadoCliente = false;
        this.motivoEliminacion = motivo;
        this.fechaEliminacion = LocalDateTime.now();
    }
}
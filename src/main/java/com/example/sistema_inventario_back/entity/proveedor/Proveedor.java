package com.example.sistema_inventario_back.entity.proveedor;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProveedor;

    @NonNull
    private String nombreComercial;

    @NonNull
    private String nombresEncargado;

    @Column(nullable = true)
    private String identificacionFiscal;

    @NonNull
    private String numeroUno;

    @Column(nullable = true)
    private String numeroDos;

    @NonNull
    private String direccion;

    @NonNull
    private LocalDateTime fechaCreacion;

    @NonNull
    private LocalDateTime fechaActualizacion;

    @NonNull
    private TipoProveedor tipoProveedor;

    @NonNull
    private Boolean estado = true;

    @Column(nullable = true)
    private String motivoEliminacion;

    @PrePersist
    public void prePersist(){
        fechaCreacion = fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }
}

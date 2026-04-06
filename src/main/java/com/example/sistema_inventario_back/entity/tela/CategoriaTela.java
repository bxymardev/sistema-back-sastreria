package com.example.sistema_inventario_back.entity.tela;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CategoriaTela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoriaTela;

    @NonNull
    private String codigoCategoria;

    @NonNull
    private String composicion;

    private String titulo;

    @NonNull
    private String peso;

    @NonNull
    private String ancho;

    private String densidad;

    @NonNull
    private String ligamento;

    @NonNull
    private String acabado;

    @Column(nullable = true)
    private String imagenBasePublicId;

    @NonNull
    private LocalDateTime fechaRegistro;

    @Column(nullable = true)
    private String motivoActualizacion;

    @OneToMany(mappedBy = "categoriaTela", cascade = CascadeType.ALL)
    private List<Tela> listaTelas;

    @PrePersist
    public void prePersist(){
        fechaRegistro = LocalDateTime.now();
    }
}
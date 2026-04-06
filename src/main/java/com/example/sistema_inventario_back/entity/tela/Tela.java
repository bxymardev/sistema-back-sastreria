package com.example.sistema_inventario_back.entity.tela;

import com.example.sistema_inventario_back.entity.compra.MateriaPrima;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Tela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTela;

    @NonNull
    private String color;

    @Column(nullable = true)
    private String codigoTela;

    @Column(nullable = true)
    private String motivoActualizacion;

    @Column(nullable = true)
    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_categoria_tela")
    private CategoriaTela categoriaTela;

    @OneToOne(mappedBy = "tela")
    private MateriaPrima materiaPrima;

    @PrePersist
    public void prePersist(){
        fechaRegistro = LocalDateTime.now();
    }
}
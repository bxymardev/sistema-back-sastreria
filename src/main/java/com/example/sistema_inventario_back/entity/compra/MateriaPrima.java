package com.example.sistema_inventario_back.entity.compra;

import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import com.example.sistema_inventario_back.entity.tela.ImagenMateriaPrima;
import com.example.sistema_inventario_back.entity.tela.Tela;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMateriaPrima;

    @NonNull
    private String nombreMateriaPrima;

    @Column(nullable = true)
    private String marca;

    @Column(nullable = true)
    private String modelo;

    @NonNull
    private Double stockActual;

    @NonNull
    private Double stockMinimo;

    @NonNull
    @Enumerated(EnumType.STRING)
    private EstadoMateriaPrima estadoMateriaPrima;

    @NonNull
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TipoMateriaPrima tipoMateriaPrima;

    @NonNull
    @Enumerated(EnumType.STRING)
    private UbicacionAlmacen ubicacionAlmacen;

    @NonNull
    private LocalDateTime fechaCreacion;

    @NonNull
    private LocalDateTime fechaActualizacion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tela", nullable = true)
    private Tela tela;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_imagen_materia_prima", nullable = true)
    private ImagenMateriaPrima imagenMateriaPrima;

    @OneToMany(mappedBy = "materiaPrima", cascade = CascadeType.ALL)
    private List<CompraDetalle> compraDetalles;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioUnitarioActual;

    public boolean esTela() {
        return TipoMateriaPrima.TELA.equals(this.tipoMateriaPrima);
    }

    @PrePersist
    public void prePersist(){
        fechaCreacion = fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }
}
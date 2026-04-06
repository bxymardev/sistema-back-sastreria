package com.example.sistema_inventario_back.entity.tela;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagenMateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagenMateriaPrima;

    private String nombreImagen;
    private String imagenUrl;
    private String imagenId;

    public ImagenMateriaPrima(String nombreImagen, String imagenUrl, String imagenId) {
        this.nombreImagen = nombreImagen;
        this.imagenUrl = imagenUrl;
        this.imagenId = imagenId;
    }
}
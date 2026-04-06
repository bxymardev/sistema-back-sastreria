package com.example.sistema_inventario_back.dto.tela;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaTelaRequestDTO {

    @NotBlank(message = "El codigo de la categoria debe ser obligatorio ")
    @Size(max = 10, message = "El codigo no debe exceder los 20 caracteres")
    private String codigoCategoria;

    @NotBlank(message = "La composicion es un campo obligatorio")
    @Size(max = 50, message = "La composicion no debe exceder los 50 caracteres")
    private String composicion;

    @Size(max = 50, message = "El titulo no debe exceder los 50 caracteres")
    private String titulo;

    @NotBlank(message = "El pero es un campo obligatorio")
    @Size(max = 50, message = "El peso no debe exceder los 50 caracteres")
    private String peso;

    @NotBlank(message = "El ancho es un campo obligatorio")
    @Size(max = 50, message = "El ancho no debe exceder los 50 caracteres")
    private String ancho;

    @Size(max = 50, message = "La densidad no debe exceder los 50 caracteres")
    private String densidad;

    @NotBlank(message = "El ligamento es un campo obligatorio")
    @Size(max = 50, message = "El ligamento no debe exceder los 50 caracteres")
    private String ligamento;

    @NotBlank(message = "El acabado es un campo obligatorio")
    @Size(max = 50, message = "El acabado no debe exceder los 50 caracteres")
    private String acabado;
}
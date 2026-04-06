package com.example.sistema_inventario_back.dto.tela;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TelaRequestDTO {

    @NotBlank(message = "El color de la tela debe ser obligatorio")
    @Size(max = 20, message = "El color no debe exceder los 20 caracteres")
    private String color;

    // Campo no obligatorio
    private String codigoTela;

    @NotNull(message = "La categoria de la tela es obligatorio")
    private Integer idCategoriaTela;
    
}
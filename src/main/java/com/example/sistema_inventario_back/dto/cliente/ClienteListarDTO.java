package com.example.sistema_inventario_back.dto.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteListarDTO {
    private Integer idCliente;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefonoUno;
}
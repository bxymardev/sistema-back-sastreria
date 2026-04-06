package com.example.sistema_inventario_back.dto.cliente;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClienteResponseDTO {
    private Integer idCliente;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String carnetIdentidad;
    private String telefono1;
    private String telefono2;
    private LocalDateTime fechaRegistro;
    private Boolean estado;
}
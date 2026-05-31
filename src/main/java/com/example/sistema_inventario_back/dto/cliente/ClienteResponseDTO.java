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
    private String telefonoUno;
    private String telefonoDos;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
    private Boolean estado;
}
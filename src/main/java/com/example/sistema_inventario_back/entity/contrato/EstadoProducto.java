package com.example.sistema_inventario_back.entity.contrato;

/**
 * Estado de fabricación de cada camisa (ítem) dentro de un contrato.
 */
public enum EstadoProducto {
    PENDIENTE,     // Aún no se ha comenzado a elaborar
    EN_PROCESO,    // En elaboración
    LISTO,         // Terminada, lista para entrega
    ENTREGADO      // Entregada al cliente
}

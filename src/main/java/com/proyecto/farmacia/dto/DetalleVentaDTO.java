package com.proyecto.farmacia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDTO {

    private Long idProducto;
    private String nombreMedicamento;
    private int cantidad;
    private double precio;
    private double subtotal;
}
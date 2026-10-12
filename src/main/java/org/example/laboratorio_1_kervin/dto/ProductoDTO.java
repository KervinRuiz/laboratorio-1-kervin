package org.example.laboratorio_1_kervin.dto;

import java.math.BigDecimal;

public record ProductoDTO(
        Long id,
        String nombre,
        String descripcion,
        Integer cantidad,
        BigDecimal precio,
        Long categoriaId
) {
}

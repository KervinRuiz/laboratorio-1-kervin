package org.example.laboratorio_1_kervin.mappers;

import org.example.laboratorio_1_kervin.dto.ProductoDTO;
import org.example.laboratorio_1_kervin.model.Productos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductoMapper {

    @Mapping(source = "categoria.id", target = "categoriaId")
    ProductoDTO toDto(Productos producto);

    List<ProductoDTO> toDtoList(List<Productos> productos);

    @Mapping(target = "categoria", ignore = true)
    Productos toEntity(ProductoDTO productosDTO);
}

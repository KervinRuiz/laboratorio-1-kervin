package org.example.laboratorio_1_kervin.mappers;

import org.example.laboratorio_1_kervin.dto.CategoriaDTO;
import org.example.laboratorio_1_kervin.model.Categorias;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {

    CategoriaDTO toDto(Categorias categoria);

    List<CategoriaDTO> toDtoList(List<Categorias> categorias);

    Categorias toEntity(CategoriaDTO categoriasDTO);
}

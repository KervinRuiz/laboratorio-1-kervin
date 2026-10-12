package org.example.laboratorio_1_kervin.Controller;

import org.example.laboratorio_1_kervin.dto.CategoriaDTO;
import org.example.laboratorio_1_kervin.mappers.CategoriaMapper;
import org.example.laboratorio_1_kervin.model.Categorias;
import org.example.laboratorio_1_kervin.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriasService;
    private final CategoriaMapper categoriasMapper;

    public CategoriaController(CategoriaService categoriasService,
                                CategoriaMapper categoriasMapper) {
        this.categoriasService = categoriasService;
        this.categoriasMapper = categoriasMapper;
    }

    @GetMapping
    public List<CategoriaDTO> findAll() {
        log.info("Listando todas las categorias");
        return categoriasMapper.toDtoList(categoriasService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        log.info("Buscando categoria con id {}", id);
        return ResponseEntity.of(
                categoriasService.findById(id)
                        .map(categoriasMapper::toDto)
        );
    }


    @PostMapping("/")
    public ResponseEntity<CategoriaDTO> create(@RequestBody CategoriaDTO categoriasDTO) {
        log.info("Creando categoria");

        Categorias creada = categoriasService.create(
                categoriasMapper.toEntity(categoriasDTO)
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(categoriasMapper.toDto(creada));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(
            @PathVariable Long id,
            @RequestBody CategoriaDTO categoriasDTO) {

        log.info("Actualizando categoria con id {}", id);

        return ResponseEntity.of(
                categoriasService.update(
                        id,
                        categoriasMapper.toEntity(categoriasDTO)
                ).map(categoriasMapper::toDto)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando categoria con id {}", id);

        return categoriasService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

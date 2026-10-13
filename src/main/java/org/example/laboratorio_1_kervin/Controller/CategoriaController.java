package org.example.laboratorio_1_kervin.Controller;

import org.example.laboratorio_1_kervin.dto.CategoriaDTO;
import org.example.laboratorio_1_kervin.mappers.CategoriaMapper;
import org.example.laboratorio_1_kervin.model.Categorias;
import org.example.laboratorio_1_kervin.services.CategoriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasAnyRole('SUPER-ADMIN-ROLE', 'USER')")
    public List<CategoriaDTO> findAll() {
        log.info("Listando todas las categorias");
        return categoriasMapper.toDtoList(categoriasService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER-ADMIN-ROLE', 'USER')")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable Long id) {
        log.info("Buscando categoria con id {}", id);
        return ResponseEntity.of(
                categoriasService.findById(id)
                        .map(categoriasMapper::toDto)
        );
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
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
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
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
    @PreAuthorize("hasRole('SUPER-ADMIN-ROLE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando categoria con id {}", id);

        return categoriasService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

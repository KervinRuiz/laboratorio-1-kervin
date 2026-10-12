package org.example.laboratorio_1_kervin.Controller;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorio_1_kervin.dto.ProductoDTO;
import org.example.laboratorio_1_kervin.mappers.ProductoMapper;
import org.example.laboratorio_1_kervin.model.Productos;
import org.example.laboratorio_1_kervin.services.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Slf4j
public class ProductoController {

    private final ProductoService productosService;
    private final ProductoMapper productosMapper;

    public ProductoController(ProductoService productosService,
                               ProductoMapper productosMapper) {
        this.productosService = productosService;
        this.productosMapper = productosMapper;
    }

    @PreAuthorize("hasAuthority('producto:leer')")
    @GetMapping
    public List<ProductoDTO> findAll() {
        log.info("Listando todos los productos");
        return productosMapper.toDtoList(productosService.findAll());
    }

    @PreAuthorize("hasAuthority('producto:leer')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id) {
        log.info("Buscando producto con id {}", id);

        return ResponseEntity.of(
                productosService.findById(id)
                        .map(productosMapper::toDto)
        );
    }

    @PreAuthorize("hasAuthority('producto:escribir')")
    @PostMapping("/")
    public ResponseEntity<ProductoDTO> create(@RequestBody ProductoDTO productosDTO) {
        log.info("Creando producto");

        Productos creado = productosService.create(
                productosMapper.toEntity(productosDTO),
                productosDTO.categoriaId()
        );

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(productosMapper.toDto(creado));
    }

    @PreAuthorize("hasAuthority('producto:escribir')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(
            @PathVariable Long id,
            @RequestBody ProductoDTO productosDTO) {

        log.info("Actualizando producto con id {}", id);

        return ResponseEntity.of(
                productosService.update(
                        id,
                        productosMapper.toEntity(productosDTO),
                        productosDTO.categoriaId()
                ).map(productosMapper::toDto)
        );
    }

    @PreAuthorize("hasAuthority('producto:eliminar')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando producto con id {}", id);

        return productosService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

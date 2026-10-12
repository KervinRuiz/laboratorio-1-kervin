package org.example.laboratorio_1_kervin.services;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorio_1_kervin.model.Categorias;
import org.example.laboratorio_1_kervin.model.Productos;
import org.example.laboratorio_1_kervin.repository.CategoriaRepository;
import org.example.laboratorio_1_kervin.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductoService {

    private final ProductoRepository productosRepository;
    private final CategoriaRepository categoriasRepository;

    public ProductoService(ProductoRepository productosRepository,
                            CategoriaRepository categoriasRepository) {
        this.productosRepository = productosRepository;
        this.categoriasRepository = categoriasRepository;
    }

    @Transactional(readOnly = true)
    public List<Productos> findAll() {
        log.info("Listando todos los productos");
        return productosRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Productos> findById(Long id) {
        log.info("Buscando producto con id {}", id);
        return productosRepository.findById(id);
    }

    @Transactional
    public Productos create(Productos producto, Long categoriaId) {
        log.info("Creando producto");

        Categorias categoria = categoriasRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoria con id " + categoriaId + " no existe"
                ));

        producto.setId(null);
        producto.setCategoria(categoria);

        return productosRepository.save(producto);
    }

    @Transactional
    public Optional<Productos> update(Long id, Productos producto, Long categoriaId) {
        log.info("Actualizando producto con id {}", id);

        return productosRepository.findById(id).map(actual -> {
            Categorias categoria = categoriasRepository.findById(categoriaId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "La categoria con id " + categoriaId + " no existe"
                    ));

            actual.setNombre(producto.getNombre());
            actual.setDescripcion(producto.getDescripcion());
            actual.setCantidad(producto.getCantidad());
            actual.setPrecio(producto.getPrecio());
            actual.setCategoria(categoria);

            return productosRepository.save(actual);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        log.info("Eliminando producto con id {}", id);

        if (!productosRepository.existsById(id)) {
            return false;
        }

        productosRepository.deleteById(id);
        return true;
    }
}

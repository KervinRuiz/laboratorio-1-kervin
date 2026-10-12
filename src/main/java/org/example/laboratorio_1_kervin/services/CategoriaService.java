package org.example.laboratorio_1_kervin.services;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratorio_1_kervin.model.Categorias;
import org.example.laboratorio_1_kervin.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CategoriaService {

    private CategoriaRepository categoriasRepository = null;

    public CategoriaService(CategoriaRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    public List<Categorias> findAll() {
        return categoriasRepository.findAll();
    }

    public Optional<Categorias> findById(Long id) {
        log.info("Buscando categoria por id: {}", id);
        return categoriasRepository.findById(id);
    }

    public Categorias create(Categorias categoria) {
        categoria.setId(null);
        return categoriasRepository.save(categoria);
    }

    @Transactional
    public Optional<Categorias> update(Long id, Categorias datos) {
        return categoriasRepository.findById(id).map(categoria -> {
            categoria.setNombre(datos.getNombre());
            categoria.setDescripcion(datos.getDescripcion());
            return categoriasRepository.save(categoria);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (!categoriasRepository.existsById(id)) {
            return false;
        }
        categoriasRepository.deleteById(id);
        return true;
    }
}

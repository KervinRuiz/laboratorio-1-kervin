package org.example.laboratorio_1_kervin.repository;

import org.example.laboratorio_1_kervin.model.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categorias, Long> {

    @Override
    List<Categorias> findAll();

    @Override
    Optional<Categorias> findById(Long id);

}
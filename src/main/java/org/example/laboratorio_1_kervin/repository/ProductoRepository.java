package org.example.laboratorio_1_kervin.repository;

import org.example.laboratorio_1_kervin.model.Productos;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, Long> {

    @Override
    @EntityGraph(attributePaths = {"categoria"})
    List<Productos> findAll();

    @Override
    @EntityGraph(attributePaths = {"categoria"})
    Optional<Productos> findById(Long id);
}

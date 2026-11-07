package org.example.laboratorio_1_kervin.repository;

import org.example.laboratorio_1_kervin.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String Nombre);
}

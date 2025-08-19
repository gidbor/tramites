package com.sic.tramites.repository;

import com.sic.tramites.model.Dependencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DependenciaRepository extends JpaRepository<Dependencia, Long> {
    Optional<Dependencia> findByCodigo(String codigo);
    Optional<Dependencia> findByNombre(String nombre);
}

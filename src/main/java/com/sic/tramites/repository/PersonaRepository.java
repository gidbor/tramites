package com.sic.tramites.repository;

import com.sic.tramites.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);
    Optional<Persona> findByUsuarioUsername(String username);
}

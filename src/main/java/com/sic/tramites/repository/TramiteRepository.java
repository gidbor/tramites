package com.sic.tramites.repository;

import com.sic.tramites.model.Persona;
import com.sic.tramites.model.Tramite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    List<Tramite> findByPersonaId(Long personaId);
    List<Tramite> findByEmpleadoId(Long empleadoId);
    Optional<Tramite> findByNumeroRadicado(String numeroRadicado);
    List<Tramite> findByPersona(Persona persona);
}
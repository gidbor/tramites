package com.sic.tramites.services;

import com.sic.tramites.model.*;
import com.sic.tramites.model.enums.EstadoTramite;
import com.sic.tramites.model.enums.Rol;
import com.sic.tramites.repository.PersonaRepository;
import com.sic.tramites.repository.TramiteRepository;
import com.sic.tramites.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TramiteService {

    private final TramiteRepository tramiteRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Listar todos
    public List<Tramite> listar() {
        return tramiteRepository.findAll();
    }

    // Buscar por ID
    public Optional<Tramite> obtenerPorId(Long id) {
        return tramiteRepository.findById(id);
    }

    // Buscar por radicado
    public Optional<Tramite> obtenerPorRadicado(String radicado) {
        return tramiteRepository.findByNumeroRadicado(radicado);
    }

    // Crear trámite (cuando ya tienes la entidad construida)
    public Tramite crear(Tramite tramite) {
        return tramiteRepository.save(tramite);
    }

    // Actualizar trámite (empleado cambia estado o empleado asignado)
    public Optional<Tramite> actualizar(Long id, Tramite datos) {
        return tramiteRepository.findById(id).map(t -> {
            t.setEstado(datos.getEstado());
            t.setEmpleado(datos.getEmpleado());
            return tramiteRepository.save(t);
        });
    }

    //empleado crea trámite + persona + usuario si no existe
    public Tramite crearTramiteConPersona(
            String nombre,
            String descripcion,
            String tipoIdentificacion,
            String numeroIdentificacion,
            String nombres,
            String apellidos,
            String usernameEmpleado
    ) {
        // 1. Obtener empleado desde el usuario logueado
        Usuario usuarioEmpleado = usuarioRepository.findByUsername(usernameEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Empleado empleado = usuarioEmpleado.getEmpleado();

        // 2. Buscar o crear persona
        Persona persona = personaRepository.findByTipoIdentificacionAndNumeroIdentificacion(
                        tipoIdentificacion, numeroIdentificacion)
                .orElseGet(() -> {
                    Persona nueva = new Persona();
                    nueva.setTipoIdentificacion(tipoIdentificacion);
                    nueva.setNumeroIdentificacion(numeroIdentificacion);
                    nueva.setNombres(nombres);
                    nueva.setApellidos(apellidos);
                    Persona saved = personaRepository.save(nueva);

                    // Crear usuario asociado a la persona
                    Usuario usuario = new Usuario();
                    usuario.setUsername(numeroIdentificacion);
                    usuario.setPassword(numeroIdentificacion); // default
                    usuario.setRol(Rol.ROLE_PERSONA);
                    usuario.setPersona(saved);
                    usuarioRepository.save(usuario);

                    return saved;
                });

        // 3. Crear trámite
        Tramite tramite = new Tramite();
        tramite.setNombre(nombre);
        tramite.setDescripcion(descripcion);
        tramite.setEstado(EstadoTramite.EN_PROCESO);
        tramite.setFechaCreacion(LocalDateTime.now());
        tramite.setNumeroRadicado(UUID.randomUUID().toString().substring(0, 8));
        tramite.setPersona(persona);
        tramite.setEmpleado(empleado);

        return tramiteRepository.save(tramite);
    }
}

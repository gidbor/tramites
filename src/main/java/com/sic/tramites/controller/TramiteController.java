package com.sic.tramites.controller;

import com.sic.tramites.model.Tramite;
import com.sic.tramites.services.TramiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tramites")
@RequiredArgsConstructor
public class TramiteController {

    private final TramiteService service;

    @GetMapping
    public List<Tramite> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tramite> obtener(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/radicado/{numero}")
    public ResponseEntity<Tramite> obtenerPorRadicado(@PathVariable String numero) {
        return service.obtenerPorRadicado(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tramite> crear(@Valid @RequestBody Tramite tramite) {
        Tramite guardado = service.crear(tramite);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    //empleado crea trámite con persona
    @PostMapping("/empleado")
    public ResponseEntity<Tramite> crearConPersona(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String tipoIdentificacion,
            @RequestParam String numeroIdentificacion,
            @RequestParam(required = false) String nombres,
            @RequestParam(required = false) String apellidos
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usernameEmpleado = auth.getName();

        Tramite tramite = service.crearTramiteConPersona(
                nombre,
                descripcion,
                tipoIdentificacion,
                numeroIdentificacion,
                nombres,
                apellidos,
                usernameEmpleado
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(tramite);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tramite> actualizar(@PathVariable Long id, @Valid @RequestBody Tramite datos) {
        return service.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar no permitido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}

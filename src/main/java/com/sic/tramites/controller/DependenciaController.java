package com.sic.tramites.controller;

import com.sic.tramites.model.Dependencia;
import com.sic.tramites.repository.DependenciaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dependencias")
@RequiredArgsConstructor
public class DependenciaController {

    private final DependenciaRepository repo;

    @GetMapping
    public List<Dependencia> listar() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<Dependencia> crear(@Valid @RequestBody Dependencia dependencia) {
        Dependencia guardada = repo.save(dependencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }
}
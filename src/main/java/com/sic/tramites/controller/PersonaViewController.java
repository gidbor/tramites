package com.sic.tramites.controller;

import com.sic.tramites.model.Persona;
import com.sic.tramites.model.Tramite;
import com.sic.tramites.repository.PersonaRepository;
import com.sic.tramites.repository.TramiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/persona")
@RequiredArgsConstructor
public class PersonaViewController {

    private final PersonaRepository personaRepository;
    private final TramiteRepository tramiteRepository;

    @GetMapping
    public String index() {
        return "persona/index";
    }


    @GetMapping("/mis-tramites")
    public String verMisTramites(@AuthenticationPrincipal User user, Model model) {
        // Usuario autenticado
        String username = user.getUsername();

        // Buscar persona asociada a ese username
        Persona persona = personaRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new RuntimeException("No se encontró la persona para el usuario: " + username));

        // Trámites de esa persona
        List<Tramite> tramites = tramiteRepository.findByPersona(persona);

        model.addAttribute("persona", persona);
        model.addAttribute("tramites", tramites);

        return "persona/mis-tramites"; // templates/persona/mis-tramites.html
    }
}

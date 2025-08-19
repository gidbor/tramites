package com.sic.tramites.controller;

import com.sic.tramites.model.Empleado;
import com.sic.tramites.model.Persona;
import com.sic.tramites.model.Tramite;
import com.sic.tramites.model.Usuario;
import com.sic.tramites.services.TramiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/empleado")
@RequiredArgsConstructor
public class EmpleadoViewController {

    private final TramiteService tramiteService;

    @GetMapping
    public String index() {
        return "empleado/index";
    }

    @GetMapping("/tramites")
    public String listarTramites(Model model) {
        model.addAttribute("tramites", tramiteService.listar());
        return "empleado/tramites";
    }

    @PostMapping("/tramites")
    public String crearTramite(
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String tipoIdentificacion,
            @RequestParam String numeroIdentificacion,
            @RequestParam(required = false) String nombres,
            @RequestParam(required = false) String apellidos
    ) {
        // Obtener usuario logueado desde SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        tramiteService.crearTramiteConPersona(
                nombre,
                descripcion,
                tipoIdentificacion,
                numeroIdentificacion,
                nombres,
                apellidos,
                username
        );

        return "redirect:/empleado/tramites";
    }
}

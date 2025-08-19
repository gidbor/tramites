package com.sic.tramites.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();
            if (role.equals("ROLE_PERSONA")) {
                return "redirect:/persona";
            } else if (role.equals("ROLE_EMPLEADO")) {
                return "redirect:/empleado";
            } else if (role.equals("ROLE_ADMIN")) {
                return "redirect:/admin";
            }
        }
        return "redirect:/login?error"; // fallback
    }
}

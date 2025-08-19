package com.sic.tramites.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // ⚠️ Para pruebas. Usa BCryptPasswordEncoder en producción.
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/mis-tramites").hasRole("PERSONA")
                        .requestMatchers("/api/empleados/**").hasAnyRole("EMPLEADO", "ADMIN")
                        .requestMatchers("/api/personas/**").hasAnyRole("EMPLEADO", "ADMIN")
                        .requestMatchers("/api/tramites/**").hasAnyRole("PERSONA", "EMPLEADO", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(customizer -> {}) // para pruebas con Postman/curl
                .formLogin(form -> form
                        .loginPage("/login")              // login Thymeleaf
                        .defaultSuccessUrl("/default", true) // redirigir después del login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}

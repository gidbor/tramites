package com.sic.tramites.model;

import com.sic.tramites.model.enums.Rol;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // encriptado con BCrypt

    @Enumerated(EnumType.STRING)
    private Rol rol;

    //asociación a Persona o Empleado
    @OneToOne
    private Persona persona;

    @OneToOne
    private Empleado empleado;
}
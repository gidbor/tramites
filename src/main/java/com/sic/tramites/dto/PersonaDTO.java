package com.sic.tramites.dto;

import lombok.Data;

@Data
public class PersonaDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String direccion;
    private String telefono;
    private String email;
    private String username;
    private String password;
}
package com.sic.tramites.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmpleadoDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String codigo;
    private LocalDate fechaIngreso;
    private DependenciaDTO dependencia;
    private String username;
    private String password;
}
package com.sic.tramites.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TramiteDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String numeroRadicado;
    private LocalDateTime fechaCreacion;
    private PersonaDTO persona;
    private EmpleadoDTO empleado;
}
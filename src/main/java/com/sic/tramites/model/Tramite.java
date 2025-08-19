package com.sic.tramites.model;

import com.sic.tramites.model.enums.EstadoTramite;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "tramite",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tramite_numero_radicado", columnNames = {"numero_radicado"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tramite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "El nombre de trámite es obligatorio")
    @Column(length = 100, nullable = false)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(length = 500, nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EstadoTramite estado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "numero_radicado", nullable = false, unique = true, updatable = false, length = 50)
    private String numeroRadicado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "persona_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tramite_persona"))
    private Persona persona;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tramite_empleado"))
    private Empleado empleado;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoTramite.EN_PROCESO;
        // Generar número de radicado único
        this.numeroRadicado = "RAD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
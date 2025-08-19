package com.sic.tramites.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "empleado",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_empleado_codigo", columnNames = {"codigo"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "El código del empleado es obligatorio")
    @Column(length = 20, nullable = false)
    private String codigo;

    @NotBlank(message = "El nombre del empleado es obligatorio")
    @Column(length = 100, nullable = false)
    private String nombres;

    @NotBlank(message = "El apellido del empleado es obligatorio")
    @Column(length = 100, nullable = false)
    private String apellidos;

    @Column(name = "fecha_ingreso", nullable = false, updatable = false)
    private LocalDate fechaIngreso;

    // Relación con Dependencia
    @ManyToOne(optional = false)
    @JoinColumn(name = "dependencia_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_empleado_dependencia"))
    private Dependencia dependencia;

    @PrePersist
    public void prePersist() {
        this.fechaIngreso = LocalDate.now();
    }
}
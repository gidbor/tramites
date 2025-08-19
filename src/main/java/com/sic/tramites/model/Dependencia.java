package com.sic.tramites.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(
        name = "dependencia",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_dependencia_codigo", columnNames = {"codigo"}),
                @UniqueConstraint(name = "uk_dependencia_nombre", columnNames = {"nombre"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Dependencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "El código de la dependencia es obligatorio")
    @Column(length = 20, nullable = false)
    private String codigo;

    @NotBlank(message = "El nombre de la dependencia es obligatorio")
    @Column(length = 100, nullable = false)
    private String nombre;
}

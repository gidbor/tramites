package com.sic.tramites.model;

import com.sic.tramites.validation.NumeroTelefonoValido;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(
        name = "persona",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_persona_ident", columnNames = {"tipo_identificacion", "numero_identificacion"}),
                @UniqueConstraint(name = "uk_persona_email", columnNames = {"email"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(name = "tipo_identificacion", length = 20, nullable = false)
    private String tipoIdentificacion;

    @NotBlank
    @Column(name = "numero_identificacion", length = 30, nullable = false)
    private String numeroIdentificacion;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nombres;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String apellidos;

    @NumeroTelefonoValido
    @Column(length = 30)
    private String telefono;

    @Column(length = 200)
    private String direccion;

    @NotBlank
    @Email
    @Column(length = 150, nullable = false)
    private String email;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;
}
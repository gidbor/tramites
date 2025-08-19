package com.sic.tramites.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumeroTelefonoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumeroTelefonoValido {

    String message() default "El número de Teléfono solo puede contener dígitos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
package com.sic.tramites.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumeroTelefonoValidator implements ConstraintValidator<NumeroTelefonoValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return value.matches("\\d+"); // solo dígitos
    }
}
package dev.lukmann.annotations;

import dev.lukmann.auth.dto.RegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterDto> {

    @Override
    public boolean isValid(RegisterDto registerDto, ConstraintValidatorContext constraintValidatorContext) {
        if (registerDto.getPassword() == null || registerDto.getConfirmPassword() == null) {
            return false;
        }

        return registerDto.getPassword().equals(registerDto.getConfirmPassword());
    }
}


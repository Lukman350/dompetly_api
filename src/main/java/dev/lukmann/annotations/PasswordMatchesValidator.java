package dev.lukmann.annotations;

import dev.lukmann.user.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        if (userDto.getPassword() == null || userDto.getConfirmPassword() == null) {
            return false;
        }

        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }
}


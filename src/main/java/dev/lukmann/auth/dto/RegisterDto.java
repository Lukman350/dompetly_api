package dev.lukmann.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Username field is required")
    @Size(min = 4, message = "Username field must be at least 4 characters long")
    private String username;

    @NotBlank(message = "Email field is required")
    @Email(message = "Email field is not an valid email")
    private String email;

    @NotBlank(message = "Password field is required")
    @Size(min = 6, message = "Password field must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Confirm Password field is required")
    @Size(min = 6, message = "Confirm Password field must be at least 6 characters long")
    private String confirmPassword;

}

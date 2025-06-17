package dev.lukmann.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "Email field is required")
    @Email(message = "Email field must be an valid email address")
    public String email;
    @NotBlank(message = "Password field is required")
    public String password;
}

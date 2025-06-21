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
    private String firebaseUid;
    private String firebaseAccountType;

    @NotBlank(message = "Email field is required")
    @Email(message = "Email field must be an valid email address")
    public String email;

    public String password;
}

package dev.lukmann.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePinRequest {
    @NotBlank(message = "Current pin is required")
    private String pin;

    @NotBlank(message = "New pin is required")
    private String newPin;

    @NotBlank(message = "Confirm new pin is required")
    private String confirmNewPin;
}

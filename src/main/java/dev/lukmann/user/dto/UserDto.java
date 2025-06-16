package dev.lukmann.user.dto;

import dev.lukmann.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String photoProfile;
    private String pin;
    private String token;

    public static UserDto fromDomain(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .photoProfile(user.getPhotoProfile())
                .pin(user.getPin() != null ? Base64.getEncoder().encodeToString(user.getPin().toString().getBytes()) : null)
                .token(user.getToken())
                .build();

    }
}

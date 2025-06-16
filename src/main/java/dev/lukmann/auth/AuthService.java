package dev.lukmann.auth;

import dev.lukmann.auth.dto.LoginDto;
import dev.lukmann.auth.dto.RegisterDto;
import dev.lukmann.exceptions.ServicesException;
import dev.lukmann.user.UserRepository;
import dev.lukmann.user.domain.User;
import dev.lukmann.user.dto.UserDto;
import dev.lukmann.utils.PasswordHash;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public UserDto registerUser(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Password and confirm password doesn't match");
        }

        if (userRepository.findByUsername(registerDto.getUsername()) != null) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Username is already taken by someone");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(PasswordHash.hashPassword(registerDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.persist(user);

        return UserDto.fromDomain(user);
    }

    @Transactional
    public UserDto loginUser(LoginDto loginDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginDto.getEmail()));

        if (user.isEmpty()) {
            throw new ServicesException(Response.Status.NOT_FOUND, "This email is not registered");
        }

        User found = user.get();

        if (!PasswordHash.checkPassword(found.getPassword(), loginDto.getPassword())) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Email or password is incorrect");
        }

        String token = Jwt.subject(found.getUsername())
                .groups(Set.of("user"))
                .claim("userId", found.getId().toString())
                .claim("email", found.getEmail())
                .claim("photoProfile", found.getPhotoProfile() == null ? "" : found.getPhotoProfile())
                .claim("pin", found.getPin() != null ? Base64.getEncoder().encodeToString(found.getPin().toString().getBytes()) : "")
                .expiresIn(Duration.ofDays(1))
                .sign();

        found.setToken(token);
        userRepository.persist(found);

        return UserDto.fromDomain(found);
    }

}

package dev.lukmann.user;

import dev.lukmann.exceptions.ServicesException;
import dev.lukmann.user.domain.User;
import dev.lukmann.user.dto.UserDto;
import dev.lukmann.utils.JwtUtils;
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
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public User registerUser(UserDto userDto) throws ServicesException {

        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Username is already taken by someone");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(PasswordHash.hashPassword(userDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.persist(user);

        return user;
    }

    @Transactional
    public String loginUser(String email, String password) throws Exception {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (user.isEmpty()) {
            throw new ServicesException(Response.Status.NOT_FOUND, "This email is not registered");
        }

        User found = user.get();

        if (!PasswordHash.checkPassword(found.getPassword(), password)) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Email or password is incorrect");
        }

        String token = Jwt.issuer("https://dompetly.lukmann.dev")
                .subject(found.getUsername())
                .groups(Set.of("user"))
                .claim("userId", found.getId().toString())
                .claim("email", found.getEmail())
                .claim("photoProfile", found.getPhotoProfile() == null ? "" : found.getPhotoProfile())
                .claim("pin", found.getPin() != null ? Base64.getEncoder().encodeToString(found.getPin().toString().getBytes()) : "")
                .expiresIn(Duration.ofDays(1))
                .sign(JwtUtils.loadPrivateKey());

        found.setToken(token);
        userRepository.persist(found);

        return token;
    }
}

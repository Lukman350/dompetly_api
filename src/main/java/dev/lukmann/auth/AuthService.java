package dev.lukmann.auth;

import dev.lukmann.auth.dto.LoginDto;
import dev.lukmann.auth.dto.RegisterDto;
import dev.lukmann.exceptions.ServicesException;
import dev.lukmann.user.UserRepository;
import dev.lukmann.user.domain.User;
import dev.lukmann.user.dto.UserDto;
import dev.lukmann.utils.PasswordHash;
import dev.lukmann.utils.StringUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.time.LocalDateTime;
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

        if (userRepository.findByFirebaseUid(registerDto.getFirebaseUid()) != null) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Firebase UID is already registered");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(PasswordHash.hashPassword(registerDto.getPassword()));
        user.setFirebaseUid(registerDto.getFirebaseUid());
        user.setPhotoProfile(registerDto.getPhotoProfile() != null ? registerDto.getPhotoProfile() : "");
        user.setFirebaseAccountType(registerDto.getFirebaseAccountType() != null ? registerDto.getFirebaseAccountType().toUpperCase() : null);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.persist(user);

        if ("google".equalsIgnoreCase(registerDto.getFirebaseAccountType())) {
            loginUser(
                    new LoginDto(
                            registerDto.getFirebaseUid(),
                            registerDto.getFirebaseAccountType(),
                            null,
                            null
                    )
            );
        }

        return UserDto.fromDomain(user);
    }

    @Transactional
    public UserDto loginUser(LoginDto loginDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByFirebaseUid(loginDto.getFirebaseUid()));

        if (user.isEmpty()) {
            throw new ServicesException(Response.Status.NOT_FOUND, "This account is not registered");
        }

        User found = user.get();

        String loginType = loginDto.getFirebaseAccountType();

        if ("email".equalsIgnoreCase(loginType)) {
            User emailUser = userRepository.findByEmail(loginDto.getEmail());

            if (emailUser == null || !PasswordHash.checkPassword(emailUser.getPassword(), loginDto.getPassword())) {
                throw new ServicesException(Response.Status.BAD_REQUEST, "Email or password is incorrect");
            }
            found = emailUser;
        } else if (!"google".equalsIgnoreCase(loginType)) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Unsupported login type");
        }

        String token = Jwt.subject(found.getId().toString())
                .upn(found.getId().toString())
                .groups(Set.of("user"))
                .claim("userId", found.getId().toString())
                .claim("username", found.getUsername())
                .claim("firebaseUid", found.getFirebaseUid())
                .claim("firebaseAccountType", found.getFirebaseAccountType() != null ? found.getFirebaseAccountType().toLowerCase() : "")
                .claim("email", found.getEmail())
                .claim("photoProfile", found.getPhotoProfile() == null ? "" : found.getPhotoProfile())
                .claim("pin", found.getPin() != null ? StringUtil.encodeStringToBase64(found.getPin().toString()) : "")
                .expiresIn(Duration.ofDays(1))
                .sign();

        found.setToken(token);
        userRepository.persist(found);

        return UserDto.fromDomain(found);
    }

}

package dev.lukmann.user.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User extends PanacheEntityBase {

    @Id
    @NotNull
    @Column(columnDefinition = "uuid", nullable = false)
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(length = 30, nullable = false)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(length = 50, nullable = false)
    private String email;

    @Column(name = "photo_profile")
    private String photoProfile;

    @Column(name = "pin")
    private Integer pin;

    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "firebase_uid", length = 50, unique = true)
    private String firebaseUid;

    @NotNull
    @Column(name = "firebase_account_type", length = 10, nullable = false)
    private String firebaseAccountType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}


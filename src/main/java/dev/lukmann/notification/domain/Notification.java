package dev.lukmann.notification.domain;

import dev.lukmann.user.domain.User;
import dev.lukmann.utils.enums.NotificationType;
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
@Table(name = "notifications", schema = "public")
public class Notification extends PanacheEntityBase {

    @Id
    @NotNull
    @Column(columnDefinition = "uuid", nullable = false)
    @GeneratedValue
    public UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @Column(length = 50)
    public String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public NotificationType type;

    @Column(name = "message")
    public String message;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

}


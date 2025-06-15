package dev.lukmann.account.domain;

import dev.lukmann.user.domain.User;
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
@Table(name = "accounts", schema = "public")
public class Account extends PanacheEntityBase {

    @Id
    @NotNull
    @Column(columnDefinition = "uuid", nullable = false)
    @GeneratedValue
    public UUID id;

    @NotNull
    @Column(length = 30, nullable = false)
    public String name;

    @Column(name = "balance")
    public Integer balance;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

}


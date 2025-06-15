package dev.lukmann.budget_target.domain;

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
@Table(name = "user_targets", schema = "public")
public class BudgetTarget extends PanacheEntityBase {

    @Id
    @NotNull
    @Column(columnDefinition = "uuid", nullable = false)
    @GeneratedValue
    public UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @NotNull
    @Column(length = 30, nullable = false)
    public String name;

    @NotNull
    @Column(name = "target_amount", nullable = false)
    public Integer targetAmount;

    @Column(name = "target_balance")
    public Integer targetBalance;

    @Column(name = "end_datetime")
    public LocalDateTime endDateTime;

    @Column(name = "message")
    public String message;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
}


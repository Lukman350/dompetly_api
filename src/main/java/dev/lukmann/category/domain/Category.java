package dev.lukmann.category.domain;

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
@Table(name = "budget_categories", schema = "public")
public class Category extends PanacheEntityBase {

    @Id
    @NotNull
    @Column(columnDefinition = "uuid", nullable = false)
    @GeneratedValue
    public UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @Column(name = "name", length = 80)
    public String name;

    @Column(name = "icon_path", length = 30)
    public String iconPath;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

}


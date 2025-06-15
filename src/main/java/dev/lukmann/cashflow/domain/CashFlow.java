package dev.lukmann.cashflow.domain;

import dev.lukmann.account.domain.Account;
import dev.lukmann.category.domain.Category;
import dev.lukmann.user.domain.User;
import dev.lukmann.utils.enums.CashFlowType;
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
@Table(name = "cash_flows", schema = "public")
public class CashFlow extends PanacheEntityBase {

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
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public Account account;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public Category category;

    @NotNull
    @Column(name = "datetime", nullable = false)
    public LocalDateTime datetime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cashflow_type", nullable = false)
    public CashFlowType type;

    @NotNull
    @Column(name = "amount", nullable = false)
    public Integer amount;

    @NotNull
    @Column(name = "title", length = 50, nullable = false)
    public String title;

    @Column(name = "message")
    public String message;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

}


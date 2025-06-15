package dev.lukmann.budget_target;

import dev.lukmann.budget_target.domain.BudgetTarget;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class BudgetTargetRepository implements PanacheRepositoryBase<BudgetTarget, UUID> {
}

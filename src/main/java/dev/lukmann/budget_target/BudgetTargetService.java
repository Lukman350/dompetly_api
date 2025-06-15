package dev.lukmann.budget_target;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BudgetTargetService {

    @Inject
    BudgetTargetRepository budgetTargetRepository;

}

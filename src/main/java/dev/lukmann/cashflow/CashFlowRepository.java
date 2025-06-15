package dev.lukmann.cashflow;

import dev.lukmann.cashflow.domain.CashFlow;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CashFlowRepository implements PanacheRepositoryBase<CashFlow, UUID> {
}

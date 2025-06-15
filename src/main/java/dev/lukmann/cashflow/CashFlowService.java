package dev.lukmann.cashflow;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CashFlowService {

    @Inject
    CashFlowRepository cashFlowRepository;

}

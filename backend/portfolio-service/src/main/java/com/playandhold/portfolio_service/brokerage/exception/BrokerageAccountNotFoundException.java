package com.playandhold.portfolio_service.brokerage.exception;

import java.util.UUID;

public class BrokerageAccountNotFoundException extends RuntimeException {
    public BrokerageAccountNotFoundException(UUID portfolioId, UUID brokerageAccountId) {
        super(
                "Brokerage account with id "
                        + brokerageAccountId
                        + " was not found in portfolio "
                        + portfolioId
        );
    }
}
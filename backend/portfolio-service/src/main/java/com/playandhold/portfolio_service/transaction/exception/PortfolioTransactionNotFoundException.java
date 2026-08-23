package com.playandhold.portfolio_service.transaction.exception;

import java.util.UUID;

public class PortfolioTransactionNotFoundException extends RuntimeException {
    public PortfolioTransactionNotFoundException(UUID portfolioId, UUID transactionId) {
        super(
                "Transaction with id "
                        + transactionId
                        + " was not found in portfolio "
                        + portfolioId
        );
    }
}
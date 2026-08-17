package com.playandhold.portfolio_service.brokerage.dto;

import java.util.UUID;

public record BrokerageAccountResponse(
        UUID id,
        UUID portfolioId,
        String brokerName,
        String accountName,
        String currency,
        boolean active

) {
}

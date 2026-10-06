package com.playandhold.portfolio_service.brokerage.dto;

import com.playandhold.portfolio_service.brokerage.LotAllocationMethod;
import com.playandhold.portfolio_service.brokerage.SellMode;

import java.util.UUID;

public record BrokerageAccountResponse(
        UUID id,
        UUID portfolioId,
        String brokerName,
        String accountName,
        String currency,
        SellMode sellMode,
        LotAllocationMethod lotAllocationMethod,
        boolean active
) {
}

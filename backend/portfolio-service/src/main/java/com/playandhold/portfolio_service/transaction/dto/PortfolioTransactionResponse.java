package com.playandhold.portfolio_service.transaction.dto;

import com.playandhold.portfolio_service.transaction.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PortfolioTransactionResponse(

        UUID id,
        UUID portfolioId,
        UUID tradableAssetId,
        UUID brokerageAccountId,
        TransactionType transactionType,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal fees,
        Instant executedAt,
        String notes

) {
}
package com.playandhold.portfolio_service.transaction.dto;

import com.playandhold.portfolio_service.transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record UpdatePortfolioTransactionRequest(

        @NotNull(message = "Tradable asset id is required")
        UUID tradableAssetId,

        @NotNull(message = "Brokerage account id is required")
        UUID brokerageAccountId,

        @NotNull(message = "Transaction type is required")
        TransactionType transactionType,

        @NotNull(message = "Quantity is required")
        @DecimalMin(
                value = "0.00000001",
                message = "Quantity must be greater than 0"
        )
        BigDecimal quantity,

        @NotNull(message = "Price is required")
        @DecimalMin(
                value = "0.00000001",
                message = "Price must be greater than 0"
        )
        BigDecimal price,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal fees,

        @NotNull
        @PastOrPresent
        Instant executedAt,

        @Size(max = 500)
        String notes

) {
}
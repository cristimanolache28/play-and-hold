package com.playandhold.portfolio_service.transaction.dto;

import com.playandhold.portfolio_service.transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreatePortfolioTransactionRequest(

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

        @NotNull(message = "Transaction date is required")
        @PastOrPresent(message = "Transaction date cannot be in the future")
        LocalDate transactionDate

) {
}

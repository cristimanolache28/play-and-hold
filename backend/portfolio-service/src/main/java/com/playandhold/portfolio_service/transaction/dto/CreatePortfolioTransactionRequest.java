package com.playandhold.portfolio_service.transaction.dto;

import com.playandhold.portfolio_service.transaction.TransactionType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreatePortfolioTransactionRequest(

        @NotBlank(message = "Symbol is required")
        @Size(max = 20, message = "Symbol must not exceed 20 characters")
        String symbol,

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

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal fees,

        @PastOrPresent
        Instant executedAt,

        @Size(max = 500)
        String notes

) {
}

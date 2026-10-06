package com.playandhold.portfolio_service.brokerage.dto;

import com.playandhold.portfolio_service.brokerage.LotAllocationMethod;
import com.playandhold.portfolio_service.brokerage.SellMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateBrokerageAccountRequest(

        @NotBlank(message = "Broker name is required")
        @Size(max = 100, message = "Broker name must not exceed 100 characters")
        String brokerName,

        @Size(max = 100, message = "Account name must not exceed 100 characters")
        String accountName,

        @NotBlank(message = "Currency is required")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "Currency must contain exactly 3 uppercase letters"
        )
        String currency,

        @NotNull(message = "Sell mode is required")
        SellMode sellMode,

        LotAllocationMethod lotAllocationMethod

) {
}

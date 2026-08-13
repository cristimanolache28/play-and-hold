package com.playandhold.portfolio_service.asset.dto;

import com.playandhold.portfolio_service.asset.AssetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateTradableAssetRequest(

        @NotBlank(message = "Symbol is required")
        @Size(
                max = 20,
                message = "Symbol must not exceed 20 characters"
        )
        String symbol,

        @NotBlank(message = "Display name is required")
        @Size(
                max = 150,
                message = "Display name must not exceed 150 characters"
        )
        String displayName,

        @NotBlank(message = "Exchange code is required")
        @Size(
                max = 20,
                message = "Exchange code must not exceed 20 characters"
        )
        String exchangeCode,

        @NotBlank(message = "Currency is required")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "Currency must contain exactly 3 uppercase letters"
        )
        String currency,

        @NotNull(message = "Asset type is required")
        AssetType assetType,

        @Size(
                max = 100,
                message = "Sector must not exceed 100 characters"
        )
        String sector,

        @Size(
                max = 100,
                message = "Industry must not exceed 100 characters"
        )
        String industry
){

}
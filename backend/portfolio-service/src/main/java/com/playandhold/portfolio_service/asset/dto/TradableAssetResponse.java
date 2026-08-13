package com.playandhold.portfolio_service.asset.dto;

import com.playandhold.portfolio_service.asset.AssetType;

import java.util.UUID;

public record TradableAssetResponse (
        UUID id,
        String symbol,
        String displayName,
        String exchangeCode,
        String currency,
        AssetType assetType,
        String sector,
        String industry,
        boolean active
){
}

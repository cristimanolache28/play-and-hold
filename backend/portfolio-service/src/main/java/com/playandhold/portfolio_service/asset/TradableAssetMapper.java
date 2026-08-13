package com.playandhold.portfolio_service.asset;

import com.playandhold.portfolio_service.asset.dto.CreateTradableAssetRequest;
import com.playandhold.portfolio_service.asset.dto.TradableAssetResponse;
import org.springframework.stereotype.Component;


@Component
public class TradableAssetMapper {

    public TradableAsset toEntity(CreateTradableAssetRequest request) {
        TradableAsset tradableAsset = new TradableAsset();
        tradableAsset.setSymbol(request.symbol());
        tradableAsset.setDisplayName(request.displayName());
        tradableAsset.setExchangeCode(request.exchangeCode());
        tradableAsset.setCurrency(request.currency());
        tradableAsset.setAssetType(request.assetType());
        tradableAsset.setSector(request.sector());
        tradableAsset.setIndustry(request.industry());
        tradableAsset.setActive(true);

        return tradableAsset;
    }

    public TradableAssetResponse toDto(TradableAsset tradableAsset) {
        return new TradableAssetResponse(
                tradableAsset.getId(),
                tradableAsset.getSymbol(),
                tradableAsset.getDisplayName(),
                tradableAsset.getExchangeCode(),
                tradableAsset.getCurrency(),
                tradableAsset.getAssetType(),
                tradableAsset.getSector(),
                tradableAsset.getIndustry(),
                tradableAsset.isActive()
        );
    }

}

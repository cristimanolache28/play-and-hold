package com.playandhold.portfolio_service.asset;

import com.playandhold.portfolio_service.asset.dto.CreateTradableAssetRequest;
import com.playandhold.portfolio_service.asset.dto.TradableAssetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradableAssetService {

    private final TradableAssetRepository repository;
    private final TradableAssetMapper mapper;

    public TradableAssetResponse createTradableAsset(CreateTradableAssetRequest request) {
            TradableAsset tradableAsset = mapper.toEntity(request);
            TradableAsset savedTradableAsset = repository.save(tradableAsset);

            return mapper.toDto(savedTradableAsset);
    }
}

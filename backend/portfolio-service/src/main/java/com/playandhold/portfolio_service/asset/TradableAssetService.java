package com.playandhold.portfolio_service.asset;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradableAssetService {

    private final TradableAssetRepository repository;
    private final TradableAssetMapper mapper;
}

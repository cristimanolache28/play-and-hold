package com.playandhold.portfolio_service.asset;

import com.playandhold.portfolio_service.asset.dto.CreateTradableAssetRequest;
import com.playandhold.portfolio_service.asset.dto.TradableAssetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TradableAssetResponse> searchTradableAssets(String query) {
        String normalizedQuery = query.trim();
        if (normalizedQuery.isEmpty()) {
            return List.of();
        }

        return repository
                .findTop10ByActiveTrueAndSymbolContainingIgnoreCaseOrActiveTrueAndDisplayNameContainingIgnoreCase(
                        normalizedQuery,
                        normalizedQuery
                )
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}

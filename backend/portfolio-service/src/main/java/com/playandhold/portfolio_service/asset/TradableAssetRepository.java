package com.playandhold.portfolio_service.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TradableAssetRepository extends JpaRepository<TradableAsset, UUID> {
    Optional<TradableAsset> findBySymbolIgnoreCase(String symbol);

    List<TradableAsset>
    findTop10ByActiveTrueAndSymbolContainingIgnoreCaseOrActiveTrueAndDisplayNameContainingIgnoreCase(
            String symbol,
            String displayName
    );
}

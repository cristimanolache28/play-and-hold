package com.playandhold.portfolio_service.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TradableAssetRepository extends JpaRepository<TradableAsset, UUID> {
}

package com.playandhold.portfolio_service.asset;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "tradable_assets",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_asset_symbol_exchange",
                        columnNames = {"symbol", "exchange_code"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradableAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tradable_asset_id", nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String symbol;

    @Column(name = "display_name", nullable = false, length = 150)
    private String displayName;

    @Column(name = "exchange_code", nullable = false, length = 20)
    private String exchangeCode;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type", nullable = false, length = 20)
    private AssetType assetType;

    @Column(length = 100)
    private String sector;

    @Column(length = 100)
    private String industry;

    @Column(nullable = false)
    private boolean active;
}

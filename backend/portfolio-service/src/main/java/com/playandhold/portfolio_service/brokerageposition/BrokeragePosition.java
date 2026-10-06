package com.playandhold.portfolio_service.brokerageposition;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "brokerage_positions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrokeragePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "brokerage_position_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "portfolio_id", nullable = false, updatable = false)
    private UUID portfolioId;

    @Column(name = "tradable_asset_id", nullable = false, updatable = false)
    private UUID tradableAssetId;

    @Column(name = "brokerage_account_id", nullable = false, updatable = false)
    private UUID brokerageAccountId;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(
            name = "average_buy_price",
            nullable = false,
            precision = 19,
            scale = 8
    )
    private BigDecimal averageBuyPrice;
}
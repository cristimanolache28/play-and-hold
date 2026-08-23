package com.playandhold.portfolio_service.positionlot;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "position_lots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionLot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "position_lot_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "portfolio_id", nullable = false, updatable = false)
    private UUID portfolioId;

    @Column(name = "tradable_asset_id", nullable = false, updatable = false)
    private UUID tradableAssetId;

    @Column(name = "brokerage_account_id", nullable = false, updatable = false)
    private UUID brokerageAccountId;

    @Column(name = "buy_transaction_id", nullable = false, updatable = false, unique = true)
    private UUID buyTransactionId;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LotStatus status;
}
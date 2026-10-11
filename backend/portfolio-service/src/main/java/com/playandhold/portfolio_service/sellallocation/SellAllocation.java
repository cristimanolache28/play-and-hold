package com.playandhold.portfolio_service.sellallocation;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "sell_allocations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_sell_allocation_transaction_lot",
                        columnNames = {
                                "sell_transaction_id",
                                "position_lot_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sell_allocation_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "sell_transaction_id", nullable = false, updatable = false)
    private UUID sellTransactionId;

    @Column(name = "position_lot_id", nullable = false, updatable = false)
    private UUID positionLotId;

    @Column(nullable = false, precision = 19, scale = 8)
    private BigDecimal quantity;
}
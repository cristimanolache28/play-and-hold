package com.playandhold.portfolio_service.sellallocation;

import com.playandhold.portfolio_service.positionlot.PositionLot;
import com.playandhold.portfolio_service.positionlot.PositionLotService;
import com.playandhold.portfolio_service.transaction.PortfolioTransaction;
import com.playandhold.portfolio_service.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellAllocationService {

    private final SellAllocationRepository sellAllocationRepository;
    private final PositionLotService positionLotService;

    @Transactional
    public void applyFifoSell(
            PortfolioTransaction sellTransaction
    ) {
        if (sellTransaction.getTransactionType() != TransactionType.SELL) {throw new IllegalArgumentException(
                    "Sell allocation can only be created from a SELL transaction"
            );
        }

        List<PositionLot> openLots =
                positionLotService.getOpenLotsForFifo(
                        sellTransaction.getPortfolioId(),
                        sellTransaction.getTradableAssetId(),
                        sellTransaction.getBrokerageAccountId()
                );

        BigDecimal availableQuantity = openLots.stream()
                .map(PositionLot::getQuantity)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        if (sellTransaction.getQuantity().compareTo(availableQuantity) > 0) {
            throw new IllegalArgumentException("Insufficient quantity available for sell");
        }

        BigDecimal remainingToSell =
                sellTransaction.getQuantity();

        for (PositionLot lot : openLots) {
            if (remainingToSell.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }

            BigDecimal allocationQuantity =  lot.getQuantity().min(remainingToSell);

            SellAllocation allocation =
                    SellAllocation.builder()
                            .sellTransactionId(
                                    sellTransaction.getId()
                            )
                            .positionLotId(
                                    lot.getId()
                            )
                            .quantity(
                                    allocationQuantity
                            )
                            .build();

            sellAllocationRepository.save(allocation);

            positionLotService.consumeQuantity(lot, allocationQuantity);

            remainingToSell = remainingToSell.subtract(allocationQuantity);
        }
    }
}
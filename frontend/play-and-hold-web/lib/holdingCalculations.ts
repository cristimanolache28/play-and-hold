import type { Holding } from "@/types/Holding";

export type HoldingMetrics = {
  marketValue: number;
  profitLoss: number;
  returnPercentage: number;
};

export function calculateHoldingMetrics(
  holding: Holding
): HoldingMetrics {
  const marketValue =
    holding.quantity * holding.marketPrice;

  const profitLoss =
    (holding.marketPrice - holding.averagePrice) *
    holding.quantity;

  const returnPercentage =
    holding.averagePrice > 0
      ? ((holding.marketPrice - holding.averagePrice) /
          holding.averagePrice) *
        100
      : 0;

  return {
    marketValue,
    profitLoss,
    returnPercentage,
  };
}
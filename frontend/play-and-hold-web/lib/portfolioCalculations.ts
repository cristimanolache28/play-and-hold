import type { Holding } from "@/types/Holding";

export function calculatePortfolioValue(
  holdings: Holding[]
): number {
  return holdings.reduce(
    (total, holding) =>
      total + holding.quantity * holding.marketPrice,
    0
  );
}

export function calculateInvestedValue(
  holdings: Holding[]
): number {
  return holdings.reduce(
    (total, holding) =>
      total + holding.quantity * holding.averagePrice,
    0
  );
}

export function calculateTotalProfitLoss(
  holdings: Holding[]
): number {
  const portfolioValue = calculatePortfolioValue(holdings);
  const investedValue = calculateInvestedValue(holdings);

  return portfolioValue - investedValue;
}

export function calculateTotalReturn(
  holdings: Holding[]
): number {
  const investedValue = calculateInvestedValue(holdings);

  if (investedValue === 0) {
    return 0;
  }

  const totalProfitLoss =
    calculateTotalProfitLoss(holdings);

  return (totalProfitLoss / investedValue) * 100;
}
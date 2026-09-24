import HoldingsTable from "@/components/portfolio/HoldingsTable";
import { mockHoldings } from "@/data/mockHoldings";

import {
  calculatePortfolioValue,
  calculateTotalProfitLoss,
  calculateTotalReturn,
} from "@/lib/portfolioCalculations";

import {
  formatCurrency,
  formatSignedCurrency,
  formatSignedPercentage,
} from "@/lib/formatters";

export default function PortfolioPage() {
  const portfolioValue =
    calculatePortfolioValue(mockHoldings);

  const totalProfitLoss =
    calculateTotalProfitLoss(mockHoldings);

  const totalReturn =
    calculateTotalReturn(mockHoldings);

  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold text-white">
        Portfolio
      </h1>

      <p className="mt-2 text-gray-400">
        Detailed overview of your current positions.
      </p>

      <div className="mt-8 flex items-end justify-between">
        <div>
          <p className="text-sm text-gray-400">
            Current Portfolio Value
          </p>

          <p className="mt-1 text-4xl font-semibold text-white">
            {formatCurrency(portfolioValue)}
          </p>
        </div>

        <div className="text-right">
          <p
            className={`text-lg font-medium ${
              totalProfitLoss >= 0
                ? "text-green-500"
                : "text-red-500"
            }`}
          >
            {formatSignedCurrency(totalProfitLoss)}
          </p>

          <p
            className={`text-sm ${
              totalReturn >= 0
                ? "text-green-500"
                : "text-red-500"
            }`}
          >
            {formatSignedPercentage(totalReturn)}
          </p>
        </div>
      </div>

      <div className="mt-8">
        <HoldingsTable holdings={mockHoldings} />
      </div>
    </div>
  );
}
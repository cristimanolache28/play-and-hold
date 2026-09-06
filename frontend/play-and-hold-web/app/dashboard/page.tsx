import HoldingsTable from "@/components/dashboard/HoldingsTable";
import SummaryCard from "@/components/dashboard/SummaryCard";
import { mockHoldings } from "@/data/mockHoldings";
import {
  calculatePortfolioValue,
  calculateTotalProfitLoss,
  calculateTotalReturn,
} from "@/lib/portfolioCalculations";

export default function DashboardPage() {
  const portfolioValue = calculatePortfolioValue(mockHoldings);

  const totalProfitLoss = calculateTotalProfitLoss(mockHoldings);

  const totalReturn = calculateTotalReturn(mockHoldings);

  const positionsCount = mockHoldings.length;
  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold text-gray-900">Dashboard</h1>

      <p className="mt-2 text-gray-500">
        Overview of your investment portfolio.
      </p>

      <div className="mt-8 grid grid-cols-2 gap-4">
        <SummaryCard
          title="Portfolio Value"
          value={formatCurrency(portfolioValue)}
        />

        <SummaryCard
          title="Total Return"
          value={formatSignedCurrency(totalProfitLoss)}
          change={formatSignedPercentage(totalReturn)}
          trend={
            totalProfitLoss > 0
              ? "up"
              : totalProfitLoss < 0
                ? "down"
                : "neutral"
          }
        />

        <SummaryCard
          title="Daily P/L"
          value="-$142.80"
          change="-1.12% today"
          trend="down"
        />

        <SummaryCard title="Positions" value={positionsCount.toString()} />
      </div>

      <HoldingsTable holdings={mockHoldings} />
    </div>
  );
}

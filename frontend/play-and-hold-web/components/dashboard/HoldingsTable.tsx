import type { Holding } from "@/types/Holding";
import {
  formatCurrency,
  formatSignedCurrency,
  formatSignedPercentage,
} from "@/lib/formatters";
import { calculateHoldingMetrics } from "@/lib/holdingCalculations";

type HoldingsTableProps = {
  holdings: Holding[];
};

export default function HoldingsTable({ holdings }: HoldingsTableProps) {
  return (
    <div className="mt-8 overflow-hidden rounded-xl border border-gray-200 bg-white">
      <div className="border-b border-gray-200 p-6">
        <h2 className="text-xl font-semibold text-gray-900">Holdings</h2>
      </div>

      <table className="w-full">
        <thead className="bg-gray-50">
          <tr>
            <th className="px-6 py-3 text-left text-sm font-medium text-gray-500">
              Asset
            </th>

            <th className="px-6 py-3 text-right text-sm font-medium text-gray-500">
              Shares
            </th>

            <th className="px-6 py-3 text-right text-sm font-medium text-gray-500">
              Avg Price
            </th>

            <th className="px-6 py-3 text-right text-sm font-medium text-gray-500">
              Market Price
            </th>

            <th className="px-6 py-3 text-right text-sm font-medium text-gray-500">
              Value
            </th>

            <th className="px-6 py-3 text-right text-sm font-medium text-gray-500">
              P/L
            </th>

            <th className="px-6 py-3 text-right text-sm font-medium text-gray-500">
              Return
            </th>
          </tr>
        </thead>

        <tbody>
          {holdings.map((holding) => {
            const { marketValue, profitLoss, returnPercentage } =
              calculateHoldingMetrics(holding);
            const isPositive = profitLoss >= 0;

            return (
              <tr key={holding.symbol} className="border-t border-gray-100">
                <td className="px-6 py-4">
                  <p className="font-medium text-gray-900">{holding.symbol}</p>

                  <p className="text-sm text-gray-500">{holding.companyName}</p>
                </td>

                <td className="px-6 py-4 text-right text-gray-900">
                  {holding.quantity}
                </td>

                <td className="px-6 py-4 text-right text-gray-900">
                  {formatCurrency(holding.averagePrice)}
                </td>

                <td className="px-6 py-4 text-right text-gray-900">
                  {formatCurrency(holding.marketPrice)}
                </td>

                <td className="px-6 py-4 text-right text-gray-900">
                  {formatCurrency(marketValue)}
                </td>

                <td
                  className={`px-6 py-4 text-right font-medium ${
                    isPositive ? "text-green-600" : "text-red-600"
                  }`}
                >
                  {formatSignedCurrency(profitLoss)}
                </td>

                <td
                  className={`px-6 py-4 text-right font-medium ${
                    isPositive ? "text-green-600" : "text-red-600"
                  }`}
                >
                  {formatSignedPercentage(returnPercentage)}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

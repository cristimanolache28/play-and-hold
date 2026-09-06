import SummaryCard from "@/components/dashboard/SummaryCard";

export default function DashboardPage() {
  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold text-gray-900">
        Dashboard
      </h1>

      <p className="mt-2 text-gray-500">
        Overview of your investment portfolio.
      </p>

      <div className="mt-8 grid grid-cols-2 gap-4">
        <SummaryCard
          title="Portfolio Value"
          value="$12,450.32"
          change="+3.24% this month"
          trend="up"
        />

        <SummaryCard
          title="Total Return"
          value="+$1,240.18"
          change="+11.06%"
          trend="up"
        />

        <SummaryCard
          title="Daily P/L"
          value="-$142.80"
          change="-1.12% today"
          trend="down"
        />

        <SummaryCard
          title="Positions"
          value="7"
        />
      </div>
    </div>
  );
}
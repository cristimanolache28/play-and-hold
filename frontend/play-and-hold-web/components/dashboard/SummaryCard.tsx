type SummaryCardProps = {
  title: string;
  value: string;
  change?: string;
  trend?: "up" | "down" | "neutral";
};

export default function SummaryCard({
  title,
  value,
  change,
  trend = "neutral",
}: SummaryCardProps) {
  const changeColor =
    trend === "up"
      ? "text-green-600"
      : trend === "down"
        ? "text-red-600"
        : "text-gray-500";

  return (
    <div className="rounded-xl border border-gray-200 bg-white p-6">
      <p className="text-sm text-gray-500">
        {title}
      </p>

      <p className="mt-2 text-2xl font-semibold text-gray-900">
        {value}
      </p>

      {change && (
        <p className={`mt-2 text-sm font-medium ${changeColor}`}>
          {change}
        </p>
      )}
    </div>
  );
}
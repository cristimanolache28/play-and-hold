type SummaryCardChange = {
  value: number;
  label: string;
};

type SummaryCardProps = {
  title: string;
  value: string;
  change?: SummaryCardChange;
};

export default function SummaryCard({
  title,
  value,
  change,
}: SummaryCardProps) {
  const changeColor =
    change && change.value > 0
      ? "text-green-600"
      : change && change.value < 0
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
          {change.label}
        </p>
      )}
    </div>
  );
}
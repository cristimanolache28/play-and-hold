"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Sidebar() {
  const pathname = usePathname();

  return (
    <aside className="w-64 min-h-screen border-r border-gray-800 bg-gray-950 p-6 text-gray-100">
      <h1 className="mb-8 text-2xl font-bold">
        Play & Hold
      </h1>

      <nav className="flex flex-col gap-2">
        <Link
          href="/dashboard"
          className={`rounded-md px-3 py-2 ${
            pathname === "/dashboard"
              ? "bg-gray-800"
              : "hover:bg-gray-800"
          }`}
        >
          Dashboard
        </Link>

        <Link
          href="/portfolio"
          className={`rounded-md px-3 py-2 ${
            pathname === "/portfolio"
              ? "bg-gray-800"
              : "hover:bg-gray-800"
          }`}
        >
          Portfolio
        </Link>

        <Link
          href="/transactions"
          className={`rounded-md px-3 py-2 ${
            pathname === "/transactions"
              ? "bg-gray-800"
              : "hover:bg-gray-800"
          }`}
        >
          Transactions
        </Link>
      </nav>
    </aside>
  );
}
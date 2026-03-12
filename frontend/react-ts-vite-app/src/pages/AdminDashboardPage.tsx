import { useLearningCenterAPI } from "@/hooks/useLearningCenterAPI";
import type { AdminStats } from "@/types/adminStats";
import {
  IconUsers,
  IconSchool,
  IconChalkboard,
  IconCreditCard,
} from "@tabler/icons-react";
import { Skeleton } from "@/components/ui/skeleton";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  ChartContainer,
  ChartTooltip,
  ChartTooltipContent,
  type ChartConfig,
} from "@/components/ui/chart";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";

type StatCardProps = {
  label: string;
  value: number | null;
  icon: React.ReactNode;
  description: string;
  loading: boolean;
};

function StatCard({ label, value, icon, description, loading }: StatCardProps) {
  return (
    <article
      aria-label={label}
      className="rounded-xl border bg-card text-card-foreground shadow-sm p-6 flex flex-col gap-4"
    >
      <div className="flex items-center justify-between">
        <span className="text-base font-semibold text-slate-700">
          {label}
        </span>
        <span aria-hidden="true" className="text-muted-foreground">
          {icon}
        </span>
      </div>
      {loading ? (
        <Skeleton className="h-10 w-32" aria-label="Loading" />
      ) : (
        <p
          className="text-3xl font-bold tabular-nums tracking-tight"
          aria-live="polite"
        >
          {value !== null ? value.toLocaleString() : "—"}
        </p>
      )}
      <p className="text-sm text-slate-500">{description}</p>
    </article>
  );
}

const creditsByWeekData = [
  { week: "Week 1", credits: 60 },
  { week: "Week 2", credits: 60 },
  { week: "Week 3", credits: 80 },
  { week: "Week 4", credits: 60 },
  { week: "Week 5", credits: 95 },
  { week: "Week 6", credits: 120 },
  { week: "Week 7", credits: 100 },
  { week: "Week 8", credits: 80 },
  { week: "Week 9", credits: 130 },
];

const creditsLineConfig = {
  credits: {
    label: "Credits Purchased",
    color: "hsl(var(--chart-green))",
  },
} satisfies ChartConfig;

const PIE_COLORS = ["hsl(var(--chart-green))", "hsl(var(--chart-sky))"];

const tutorStudentPieConfig = {
  tutors: {
    label: "Tutors",
    color: PIE_COLORS[0],
  },
  students: {
    label: "Students",
    color: PIE_COLORS[1],
  },
} satisfies ChartConfig;

export default function AdminDashboardPage() {
  const raw = useLearningCenterAPI<AdminStats>("/api/admin/stats");

  const stats =
    raw &&
    typeof raw === "object" &&
    "parents" in raw &&
    "students" in raw &&
    "tutors" in raw &&
    "creditsPurchased" in raw
      ? raw
      : null;

  const error =
    raw !== null && !stats ? "Failed to load admin statistics." : null;

  const loading = raw === null;

  const cards: Omit<StatCardProps, "loading">[] = [
    {
      label: "Total Parents",
      value: stats?.parents ?? null,
      icon: <IconUsers className="size-5" />,
      description: "Registered parent accounts",
    },
    {
      label: "Total Students",
      value: stats?.students ?? null,
      icon: <IconSchool className="size-5" />,
      description: "Enrolled student profiles",
    },
    {
      label: "Total Tutors",
      value: stats?.tutors ?? null,
      icon: <IconChalkboard className="size-5" />,
      description: "Active tutors on the platform",
    },
    {
      label: "Total Credits Purchased",
      value: stats?.creditsPurchased ?? null,
      icon: <IconCreditCard className="size-5" />,
      description: "Cumulative credits across all parents",
    },
  ];

  const pieData = [
    { name: "Tutors", value: stats?.tutors ?? 0 },
    { name: "Students", value: stats?.students ?? 0 },
  ];

  return (
    <main
      aria-labelledby="admin-dashboard-heading"
      className="flex flex-col gap-6"
      style={
        {
          "--chart-green": "142 76% 36%",
          "--chart-sky": "199 89% 48%",
        } as React.CSSProperties
      }
    >
      <div>
        <h1
          id="admin-dashboard-heading"
          className="text-4xl font-bold text-slate-900 tracking-tight"
        >
          Admin Dashboard
        </h1>
        <p className="text-lg text-slate-600 mt-1">
          Aggregated overview of the learning center
        </p>
      </div>

      {error && (
        <div
          role="alert"
          className="rounded-md border border-destructive bg-destructive/10 px-4 py-3 text-sm text-destructive"
        >
          {error}
        </div>
      )}

      <section
        aria-label="Key metrics"
        className="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-4"
      >
        {cards.map((card) => (
          <StatCard key={card.label} {...card} loading={loading} />
        ))}
      </section>

      <section
        aria-label="Charts"
        className="grid grid-cols-1 gap-4 lg:grid-cols-2"
      >
        <Card>
          <CardHeader>
            <CardTitle className="text-2xl font-bold text-slate-900">
              Credits Purchased by Week
            </CardTitle>
            <CardDescription className="text-base text-slate-600">
              Weekly credit purchase activity
            </CardDescription>
          </CardHeader>
          <CardContent>
            <ChartContainer config={creditsLineConfig} className="h-64 w-full">
              <LineChart data={creditsByWeekData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="week" />
                <YAxis />
                <ChartTooltip content={<ChartTooltipContent />} />
                <Line
                  type="monotone"
                  dataKey="credits"
                  stroke="var(--color-credits)"
                  strokeWidth={2}
                  dot={{ r: 4 }}
                  activeDot={{ r: 6 }}
                />
              </LineChart>
            </ChartContainer>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="text-2xl font-bold text-slate-900">
              Tutors vs Students
            </CardTitle>
            <CardDescription className="text-base text-slate-600">
              Current ratio to help assess hiring needs
            </CardDescription>
          </CardHeader>
          <CardContent className="flex items-center justify-center">
            {loading ? (
              <Skeleton className="h-64 w-64 rounded-full" />
            ) : (
              <ChartContainer
                config={tutorStudentPieConfig}
                className="h-64 w-full"
              >
                <PieChart>
                  <Pie
                    data={pieData}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    outerRadius={90}
                    label={({ name, value }) => `${name}: ${value}`}
                  >
                    {pieData.map((_, index) => (
                      <Cell
                        key={`cell-${index}`}
                        fill={PIE_COLORS[index % PIE_COLORS.length]}
                      />
                    ))}
                  </Pie>
                  <Legend />
                  <ChartTooltip content={<ChartTooltipContent />} />
                </PieChart>
              </ChartContainer>
            )}
          </CardContent>
        </Card>
      </section>
    </main>
  );
}

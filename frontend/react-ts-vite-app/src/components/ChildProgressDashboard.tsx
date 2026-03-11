import { useMemo, useState } from "react";
import {
  BarChart,
  Bar,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  PieChart,
  Pie,
  Legend,
  Cell,
} from "recharts";
import { useParams } from "react-router-dom";

import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import type { ChildProgressDashboardResponse } from "../types/progressDashboard";
import type { ChildResponse } from "../types/parents";

const COLORS = ["#2563eb", "#10b981", "#f59e0b", "#ef4444", "#8b5cf6"];

function formatDate(dt: string | null) {
  if (!dt) return "N/A";
  const d = new Date(dt);
  if (Number.isNaN(d.getTime())) return dt;

  return new Intl.DateTimeFormat(undefined, {
    year: "numeric",
    month: "numeric",
    day: "numeric",
    hour: "numeric",
    minute: "2-digit",
  }).format(d);
}

function getProgressColor(percent: number) {
  if (percent < 40) return "bg-red-500";
  if (percent < 70) return "bg-yellow-400";
  return "bg-green-500";
}

function ChildProgressSection({
  data,
}: {
  data: ChildProgressDashboardResponse;
}) {
  const hasNoProgressData = (data.totalCompletedSessions ?? 0) === 0;
  const goals = data.goals ?? [];
  const sessionsChartData = (data.sessionsOverTime ?? []).map((p) => ({
    name: p.label,
    value: p.value ?? 0,
  }));

  const subjectChartData = (data.subjectBreakdown ?? []).map((p) => ({
    name: p.label,
    value: p.value ?? 0,
  }));

  const tutorNotes = data.lastTutorNotes ?? [];

  if (hasNoProgressData) {
    return (
      <div className="border rounded-lg p-6 bg-gray-50">
        <div className="text-lg font-medium">No progress data yet.</div>
        <div className="text-sm text-gray-600">
          Book a session to start tracking progress.
        </div>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="border rounded-lg p-4">
          <div className="text-sm text-gray-500">Total completed sessions</div>
          <div className="text-3xl font-semibold">
            {data.totalCompletedSessions}
          </div>
        </div>

        <div className="border rounded-lg p-4">
          <div className="text-sm text-gray-500">Most recent session</div>
          <div className="text-base font-medium">
            {formatDate(data.mostRecentSessionDate)}
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-4">
        <div className="border rounded-lg p-4">
          <div className="font-medium mb-3">Sessions Over Time</div>
          <div className="h-64">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={sessionsChartData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis allowDecimals={false} />
                <Tooltip />
                <Bar dataKey="value" fill="#2563eb" />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        <div className="border rounded-lg p-4">
          <div className="font-medium mb-3">Subject Breakdown</div>
          <div className="h-64">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie data={subjectChartData} dataKey="value" nameKey="name">
                  {subjectChartData.map((_, index) => (
                    <Cell
                      key={`cell-${index}`}
                      fill={COLORS[index % COLORS.length]}
                    />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>

      <div className="border rounded-lg p-4">
        <div className="font-medium mb-2">Current Subjects</div>

        {goals.length === 0 ? (
          <div className="text-sm text-gray-600">No goals yet.</div>
        ) : (
          <div className="space-y-4">
            {goals.map((goal) => (
              <div key={goal.goalId} className="space-y-2">
                <div className="flex items-start justify-between gap-3">
                  <div>
                    <div className="font-medium">{goal.subject}</div>
                    <div className="text-sm text-gray-700">{goal.title}</div>
                  </div>
                  <div className="text-sm font-medium">
                    {goal.percentageComplete}%
                  </div>
                </div>

                <div className="w-full h-3 bg-gray-200 rounded-full overflow-hidden">
                  <div
                    className={`h-full rounded-full ${getProgressColor(goal.percentageComplete)}`}
                    style={{ width: `${goal.percentageComplete}%` }}
                  />
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      <div className="border rounded-lg p-4">
        <div className="font-medium mb-2">Last Tutor Notes</div>

        {tutorNotes.length === 0 ? (
          <div className="text-sm text-gray-600">No notes yet.</div>
        ) : (
          <div className="space-y-3">
            {tutorNotes.map((n, idx) => (
              <div key={idx} className="border rounded p-3">
                <div className="flex items-center justify-between gap-3">
                  <div className="font-medium">{n.label}</div>
                  <div className="text-xs text-gray-500">
                    {formatDate(n.date)}
                  </div>
                </div>
                <div className="text-sm text-gray-700 mt-1">
                  {n.extra && n.extra.trim().length > 0
                    ? n.extra
                    : "No notes yet."}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

function SingleChildProgress({
  parentId,
  child,
  groupBy,
}: {
  parentId: string;
  child: ChildResponse;
  groupBy: "week" | "month";
}) {
  const url = `/api/parents/${parentId}/children/${child.childId}/progress?groupBy=${groupBy}`;
  const data = useLearningCenterAPI<ChildProgressDashboardResponse>(url);

  return (
    <div className="space-y-2">
      <h2 className="text-lg font-semibold">
        {child.firstName}{" "}
        <span className="text-sm text-gray-500 font-normal">
          Grade {child.gradeLevel}
        </span>
      </h2>
      {!data ? (
        <div className="text-sm text-gray-600">Loading...</div>
      ) : (
        <ChildProgressSection data={data} />
      )}
    </div>
  );
}

export default function ChildProgressDashboard() {
  const { parentId } = useParams();
  const [selectedChildId, setSelectedChildId] = useState<string>("all");
  const [groupBy, setGroupBy] = useState<"week" | "month">("week");

  const children = useLearningCenterAPI<ChildResponse[]>(
    `/api/parents/${parentId}/children`,
  );
  const progressUrl = `/api/parents/${parentId}/children/${selectedChildId}/progress?groupBy=${groupBy}`;
  const data =
    useLearningCenterAPI<ChildProgressDashboardResponse>(progressUrl);

  const selectedChild = useMemo(
    () =>
      Array.isArray(children)
        ? children.find((c) => c.childId.toString() === selectedChildId)
        : undefined,
    [children, selectedChildId],
  );

  return (
    <div className="p-4 md:p-6 space-y-6">
      <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-3">
        <div>
          <h1 className="text-2xl font-semibold">Progress Dashboard</h1>
          {selectedChildId !== "all" && selectedChild && (
            <p className="text-sm text-gray-500">
              {selectedChild.firstName} • Grouping: {groupBy}
            </p>
          )}
          {selectedChildId === "all" && (
            <p className="text-sm text-gray-500">
              All Children • Grouping: {groupBy}
            </p>
          )}
        </div>

        <div className="flex items-center gap-3">
          <select
            className="border rounded px-2 py-1 text-sm"
            value={selectedChildId}
            onChange={(e) => setSelectedChildId(e.target.value)}
          >
            <option value="all">All Children</option>
            {Array.isArray(children) &&
              children.map((child) => (
                <option key={child.childId} value={child.childId}>
                  {child.firstName} (Grade {child.gradeLevel})
                </option>
              ))}
          </select>

          <select
            className="border rounded px-2 py-1 text-sm"
            value={groupBy}
            onChange={(e) => setGroupBy(e.target.value as "week" | "month")}
          >
            <option value="week">Week</option>
            <option value="month">Month</option>
          </select>
        </div>
      </div>

      {selectedChildId === "all" && Array.isArray(children) && (
        <div className="space-y-10">
          {children.map((child) => (
            <div key={child.childId}>
              <SingleChildProgress
                parentId={parentId!}
                child={child}
                groupBy={groupBy}
              />
            </div>
          ))}
        </div>
      )}

      {selectedChildId === "all" && !Array.isArray(children) && (
        <div className="text-sm text-gray-600">Loading...</div>
      )}

      {selectedChildId !== "all" && !data && (
        <div className="text-sm text-gray-600">Loading...</div>
      )}

      {selectedChildId !== "all" && data && selectedChild && (
        <ChildProgressSection data={data} />
      )}
    </div>
  );
}

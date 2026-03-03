import { useMemo, useState } from "react"
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
} from "recharts"

import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI"
import type { ChildProgressDashboardResponse } from "../types/progressDashboard"

const COLORS = ["#2563eb", "#10b981", "#f59e0b", "#ef4444", "#8b5cf6"]

type Props = {
    parentId: number
    childId: number
}

function formatDate(dt: string | null) {
    if (!dt) return "N/A"
    const d = new Date(dt)
    if (Number.isNaN(d.getTime())) return dt
    return d.toLocaleString()
}

export default function ChildProgressDashboardPage({ parentId, childId }: Props) {
    const [groupBy, setGroupBy] = useState<"week" | "month">("week")
    const [demo, setDemo] = useState(true)

    const url =
        `/api/parents/${parentId}/children/${childId}/progress` +
        `?groupBy=${groupBy}&demo=${demo}`

    const data = useLearningCenterAPI<ChildProgressDashboardResponse>(url)

    const hasNoProgressData = useMemo(() => {
        if (!data) return false
        return (data.totalCompletedSessions ?? 0) === 0
    }, [data])

    const sessionsChartData = useMemo(() => {
        if (!data) return []
        return data.sessionsOverTime.map((p) => ({
            name: p.label,
            value: p.value ?? 0,
        }))
    }, [data])

    const subjectChartData = useMemo(() => {
        if (!data) return []
        return data.subjectBreakdown.map((p) => ({
            name: p.label,
            value: p.value ?? 0,
        }))
    }, [data])

    const currentSubjects = useMemo(() => {
        if (!data) return []
        return data.currentSubjects.map((p) => p.label)
    }, [data])

    const tutorNotes = useMemo(() => {
        if (!data) return []
        return data.lastTutorNotes
    }, [data])

    return (
        <div className="p-4 md:p-6 space-y-6">
            <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-3">
                <div>
                    <h1 className="text-2xl font-semibold">Progress Dashboard</h1>
                    <p className="text-sm text-gray-500">
                        Child ID: {childId} • Grouping: {groupBy}
                    </p>
                </div>

                <div className="flex items-center gap-3">
                    <label className="text-sm flex items-center gap-2">
                        <span className="text-gray-600">Demo</span>
                        <input
                            type="checkbox"
                            checked={demo}
                            onChange={(e) => setDemo(e.target.checked)}
                        />
                    </label>

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

            {!data && <div className="text-sm text-gray-600">Loading...</div>}

            {data && hasNoProgressData && (
                <div className="border rounded-lg p-6 bg-gray-50">
                    <div className="text-lg font-medium">No progress data yet.</div>
                    <div className="text-sm text-gray-600">
                        Book a session to start tracking progress.
                    </div>
                </div>
            )}

            {data && !hasNoProgressData && (
                <>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div className="border rounded-lg p-4">
                            <div className="text-sm text-gray-500">Total completed sessions</div>
                            <div className="text-3xl font-semibold">{data.totalCompletedSessions}</div>
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
                                        <Bar dataKey="value" fill="#2563eb" />                  </BarChart>
                                </ResponsiveContainer>
                            </div>
                        </div>

                        <div className="border rounded-lg p-4">
                            <div className="font-medium mb-3">Subject Breakdown</div>
                            <div className="h-64">
                                <ResponsiveContainer width="100%" height="100%">
                                    <PieChart>
                                        <Pie data={subjectChartData} dataKey="value" nameKey="name" />
                                        <Tooltip />
                                        <Legend />
                                    </PieChart>
                                </ResponsiveContainer>
                            </div>
                        </div>
                    </div>

                    <div className="border rounded-lg p-4">
                        <div className="font-medium mb-2">Current Subjects</div>

                        {currentSubjects.length === 0 ? (
                            <div className="text-sm text-gray-600">No current subjects.</div>
                        ) : (
                            <div className="flex flex-wrap gap-2">
                                {currentSubjects.map((s) => (
                                    <span
                                        key={s}
                                        className="text-sm px-2 py-1 rounded-full border bg-gray-50"
                                    >
                                        {s}
                                    </span>
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
                                            {n.extra && n.extra.trim().length > 0 ? n.extra : "No notes yet."}
                                        </div>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </>
            )}
        </div>
    )
}
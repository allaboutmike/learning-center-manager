export type ChartPoint = {
  label: string
  value: number | null
  date: string | null
  extra: string | null
}
export type GoalProgress = {
  goalId: number
  subject: string
  title: string
  percentageComplete: number
}
export type ChildProgressDashboardResponse = {
  childId: number
  totalCompletedSessions: number
  mostRecentSessionDate: string | null
  sessionsOverTime: ChartPoint[]
  subjectBreakdown: ChartPoint[]
  currentSubjects: ChartPoint[]
  lastTutorNotes: ChartPoint[]
    goals: GoalProgress[]

}
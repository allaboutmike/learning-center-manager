import { Navigate } from "react-router-dom";
import {
  useLearningCenterAPI,
  useLearningCenterPatch,
  useLearningCenterPost,
} from "@/hooks/useLearningCenterAPI";
import type { TutorDashboard } from "@/types/tutorDashboard";
import type { Session } from "@/types/session";
import { useState } from "react";
import {
  IconUsers,
  IconChalkboard,
  IconStar,
  IconCalendar,
  IconEdit,
  IconDeviceFloppy,
  IconX,
} from "@tabler/icons-react";
import { Skeleton } from "@/components/ui/skeleton";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Badge } from "@/components/ui/badge";
import { Checkbox } from "@/components/ui/checkbox";
import { usePersona } from "@/context/usePersona";

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
        <span className="text-sm font-medium text-muted-foreground">
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
          className="text-4xl font-bold tabular-nums tracking-tight"
          aria-live="polite"
        >
          {value !== null ? value.toLocaleString() : "—"}
        </p>
      )}
      <p className="text-xs text-muted-foreground">{description}</p>
    </article>
  );
}

type SessionCardProps = {
  session: Session;
  onUpdateNotes: (
    sessionId: number,
    notes: string,
    attended: boolean
  ) => Promise<void>;
};

function SessionCard({ session, onUpdateNotes }: SessionCardProps) {
  const [editing, setEditing] = useState(false);
  const [notes, setNotes] = useState(session.sessionNotes || "");
  const [attended, setAttended] = useState(session.attended);
  const post = useLearningCenterPost();
  const [percent, setPercent] = useState<number>(0);
  const [saved, setSaved] = useState(false);

  const handleSave = async () => {
    await onUpdateNotes(session.sessionId, notes ?? "", attended);
    setEditing(false);
  };

  const handleCancel = () => {
    setNotes(session.sessionNotes || "");
    setAttended(session.attended);
    setEditing(false);
  };

  const saveProgress = async () => {
    try {
      await post(
        `/api/sessions/${session.sessionId}/progress`,
        {
          percentageComplete: percent,
        }
      );
      setSaved(true);
      setTimeout(() => setSaved(false), 2000);
    } catch (error) {
      console.error("Failed to save progress:", error);
      alert("Failed to save progress");
    }
  };

  const now = new Date();
  const sessionTime = new Date(session.time);

  return (
    <Card>
      <CardHeader>
        <div className="flex items-center justify-between">
          <div>
            <CardTitle className="text-lg">
              {typeof session.subjectName === "string"
                ? session.subjectName
                : session.subjectName.name}
            </CardTitle>
            <CardDescription>
              {session.childName} • {new Date(session.time).toLocaleString()}
            </CardDescription>
          </div>
          <Badge variant="outline">
            {new Date(session.time) > new Date() ? "Upcoming" : "Completed"}
          </Badge>
        </div>
      </CardHeader>
      <CardContent hidden={sessionTime > now}>
        <div className="space-y-4">
          <div>
            <h4 className="text-sm font-medium mb-2">Session Notes</h4>
            {editing ? (
              <div className="space-y-2">
                <Textarea
                  value={notes ?? session.sessionNotes ?? ""}
                  onChange={(e) => setNotes(e.target.value)}
                  placeholder="Add notes about this session..."
                  rows={3}
                />
              </div>
            ) : (
              <p className="text-sm text-muted-foreground">
                {session.sessionNotes || "No notes added yet."}
              </p>
            )}

            <div className="flex gap-2 mt-2">
              <Checkbox
                checked={attended}
                disabled={!editing}
                onCheckedChange={(checked) => setAttended(checked === true)}
              />
              <label
                htmlFor={`attendance -${session.sessionId}`}
                className="text-sm text-muted-foreground"
              >
                Student attended session
              </label>
            </div>

            {editing ? (
              <div className="flex gap-2">
                <Button size="sm" onClick={handleSave} className="bg-green-600 hover:bg-green-700 text-white">

                  <IconDeviceFloppy className="w-4 h-4 mr-1" />
                  Save
                </Button>
                <Button size="sm" variant="outline" onClick={handleCancel}>
                  <IconX className="w-4 h-4 mr-1" />
                  Cancel
                </Button>
              </div>
            ) : (
              <div className="space-y-2">
                <Button
                  size="sm"
                  variant="outline"
                  onClick={() => {
                    setNotes(session.sessionNotes || "");
                    setEditing(true);
                  }}
                >
                  <IconEdit className="w-4 h-4 mr-1" />
                  {session.sessionNotes ? "Edit Notes" : "Add Notes"}
                </Button>
              </div>
            )}
          </div>
          {saved && <p className="text-green-600 text-sm">Progress saved</p>}
          <div className="mt-4 space-y-2">
            <input
              type="text"
              inputMode="numeric"
              value={percent}
              onChange={(e) => setPercent(Number(e.target.value))}
              className="border p-2 rounded w-full"
              placeholder="Progress %"
            />

            <Button size="sm" className="bg-green-600 hover:bg-green-700 text-white"
              onClick={saveProgress}>
              Save Progress
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}

export default function TutorDashboardPage() {
  const { persona } = usePersona();

  const tutorId = persona.role === "tutor" ? persona.id ?? null : null;

  if (!tutorId) {
    // Navigate to persona selection
    return <Navigate to="/persona" replace />;
  }

  const [refreshKey, setRefreshKey] = useState(0);

  const dashboard = useLearningCenterAPI<TutorDashboard>(
    `/api/tutors/${tutorId}/dashboard?refresh=${refreshKey}`
  );
  const upcomingSessions = useLearningCenterAPI<Session[]>(
    `/api/tutors/${tutorId}/sessions/upcoming?refresh=${refreshKey}`
  );
  const pastSessions = useLearningCenterAPI<Session[]>(
    `/api/tutors/${tutorId}/sessions/past?refresh=${refreshKey}`
  );

  const loading =
    dashboard === null || upcomingSessions === null || pastSessions === null;

  const patch = useLearningCenterPatch();

  const updateSessionNotes = async (
    sessionId: number,
    notes: string,
    attended: boolean
  ) => {
    try {
      await patch<Session>(`/api/sessions/${sessionId}/notes`,
        { sessionNotes: notes, attended }
      );
      // Refresh data
      setRefreshKey((prev) => prev + 1);
    } catch (error) {
      console.error("Failed to update session notes:", error);
    }
  };

  const cards: Omit<StatCardProps, "loading">[] = [
    {
      label: "Total Students Tutored",
      value: dashboard?.totalStudentsTutored ?? null,
      icon: <IconUsers className="size-5" />,
      description: "Unique students you've helped",
    },
    {
      label: "Total Sessions Completed",
      value: dashboard?.totalSessionsCompleted ?? null,
      icon: <IconChalkboard className="size-5" />,
      description: "Sessions you've conducted",
    },
    {
      label: "Average Rating",
      value: dashboard?.averageRating
        ? Math.round(dashboard.averageRating * 10) / 10
        : null,
      icon: <IconStar className="size-5" />,
      description: "Based on student reviews",
    },
  ];

  return (
    <main
      aria-labelledby="tutor-dashboard-heading"
      className="flex flex-col gap-6"
    >
      <div>
        <h1
          id="tutor-dashboard-heading"
          className="text-2xl font-semibold tracking-tight"
        >
          {dashboard?.tutorName
            ? `Welcome, ${dashboard.tutorName}!`
            : "Tutor Dashboard"}
        </h1>
        <p className="text-sm text-muted-foreground mt-1">
          Overview of your tutoring sessions and performance
        </p>
      </div>

      <section
        aria-label="Key metrics"
        className="grid grid-cols-1 gap-4 sm:grid-cols-2 xl:grid-cols-3"
      >
        {cards.map((card) => (
          <StatCard key={card.label} {...card} loading={loading} />
        ))}
      </section>

      {dashboard?.nextUpcomingSession && (
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <IconCalendar className="w-5 h-5" />
              Next Upcoming Session
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-2">
              <p className="font-medium">
                {typeof dashboard.nextUpcomingSession.subjectName === "string"
                  ? dashboard.nextUpcomingSession.subjectName
                  : dashboard.nextUpcomingSession.subjectName.name}
              </p>
              <p className="text-sm text-muted-foreground">
                {dashboard.nextUpcomingSession.childName} •{" "}
                {new Date(dashboard.nextUpcomingSession.time).toLocaleString()}
              </p>
            </div>
          </CardContent>
        </Card>
      )}

      <section aria-label="Upcoming sessions" className="space-y-4">
        <h2 className="text-xl font-semibold">Upcoming Sessions</h2>
        {loading ? (
          <div className="space-y-4">
            {[...Array(3)].map((_, i) => (
              <Skeleton key={i} className="h-32" />
            ))}
          </div>
        ) : upcomingSessions && upcomingSessions.length > 0 ? (
          <div className="space-y-4">
            {upcomingSessions.map((session) => (
              <SessionCard
                key={session.sessionId}
                session={session}
                onUpdateNotes={updateSessionNotes}
              />
            ))}
          </div>
        ) : (
          <Card>
            <CardContent className="py-8 text-center">
              <p className="text-muted-foreground">No upcoming sessions</p>
            </CardContent>
          </Card>
        )}
      </section>

      <section aria-label="Past sessions" className="space-y-4">
        <h2 className="text-xl font-semibold">Past Sessions</h2>
        {loading ? (
          <div className="space-y-4">
            {[...Array(3)].map((_, i) => (
              <Skeleton key={i} className="h-32" />
            ))}
          </div>
        ) : pastSessions && pastSessions.length > 0 ? (
          <div className="space-y-4">
            {pastSessions.map((session) => (
              <SessionCard
                key={session.sessionId}
                session={session}
                onUpdateNotes={updateSessionNotes}
              />
            ))}
          </div>
        ) : (
          <Card>
            <CardContent className="py-8 text-center">
              <p className="text-muted-foreground">No past sessions</p>
            </CardContent>
          </Card>
        )}
      </section>
    </main>
  );
}

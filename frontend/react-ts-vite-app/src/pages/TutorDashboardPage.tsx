import { useParams } from "react-router-dom";
import { useLearningCenterAPI } from "@/hooks/useLearningCenterAPI";
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
  onUpdateNotes: (sessionId: number, notes: string) => void;
};

function SessionCard({ session, onUpdateNotes }: SessionCardProps) {
  const [editing, setEditing] = useState(false);
  const [notes, setNotes] = useState(session.sessionNotes || "");

  const handleSave = () => {
    onUpdateNotes(session.sessionId, notes);
    setEditing(false);
  };

  const handleCancel = () => {
    setNotes(session.sessionNotes || "");
    setEditing(false);
  };

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
      <CardContent>
        <div className="space-y-4">
          <div>
            <h4 className="text-sm font-medium mb-2">Session Notes</h4>
            {editing ? (
              <div className="space-y-2">
                <Textarea
                  value={notes}
                  onChange={(e) => setNotes(e.target.value)}
                  placeholder="Add notes about this session..."
                  rows={3}
                />
                <div className="flex gap-2">
                  <Button size="sm" onClick={handleSave}>
                    <IconDeviceFloppy className="w-4 h-4 mr-1" />
                    Save
                  </Button>
                  <Button size="sm" variant="outline" onClick={handleCancel}>
                    <IconX className="w-4 h-4 mr-1" />
                    Cancel
                  </Button>
                </div>
              </div>
            ) : (
              <div className="space-y-2">
                <p className="text-sm text-muted-foreground">
                  {session.sessionNotes || "No notes added yet."}
                </p>
                <Button
                  size="sm"
                  variant="outline"
                  onClick={() => setEditing(true)}
                >
                  <IconEdit className="w-4 h-4 mr-1" />
                  {session.sessionNotes ? "Edit Notes" : "Add Notes"}
                </Button>
              </div>
            )}
          </div>
        </div>
      </CardContent>
    </Card>
  );
}

export default function TutorDashboardPage() {
  const { tutorId: tutorIdParam } = useParams<{ tutorId: string }>();
  const tutorId = tutorIdParam ? parseInt(tutorIdParam, 10) : null;
  const [refreshKey, setRefreshKey] = useState(0);

  const dashboard = useLearningCenterAPI<TutorDashboard>(
    tutorId ? `/api/tutors/${tutorId}/dashboard?refresh=${refreshKey}` : "",
  );
  const upcomingSessions = useLearningCenterAPI<Session[]>(
    tutorId
      ? `/api/tutors/${tutorId}/sessions/upcoming?refresh=${refreshKey}`
      : "",
  );
  const pastSessions = useLearningCenterAPI<Session[]>(
    tutorId ? `/api/tutors/${tutorId}/sessions/past?refresh=${refreshKey}` : "",
  );

  const loading =
    tutorId === null ||
    dashboard === null ||
    upcomingSessions === null ||
    pastSessions === null;

  const updateSessionNotes = async (sessionId: number, notes: string) => {
    try {
      await fetch(`/api/sessions/${sessionId}/notes`, {
        method: "PUT",
        headers: {
          "Content-Type": "text/plain",
        },
        body: notes,
      });
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

  if (tutorId === null) {
    return (
      <main className="flex flex-col gap-6">
        <div>
          <h1 className="text-2xl font-semibold tracking-tight">
            Tutor Dashboard
          </h1>
          <p className="text-sm text-muted-foreground mt-1">
            Invalid tutor ID provided
          </p>
        </div>
        <Card>
          <CardContent className="py-8 text-center">
            <p className="text-muted-foreground">
              Please provide a valid tutor ID in the URL
            </p>
          </CardContent>
        </Card>
      </main>
    );
  }

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

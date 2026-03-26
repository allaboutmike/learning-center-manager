import { useNavigate } from "react-router-dom";

const mockSessions = [
  {
    id: 1,
    title: "Math Tutoring",
    tutorName: "John Smith",
    time: "Today at 4:00 PM",
    status: "LIVE",
  },
  {
    id: 2,
    title: "Reading Tutoring",
    tutorName: "Sarah Lee",
    time: "Tomorrow at 5:00 PM",
    status: "UPCOMING",
  },
];

export default function LiveSessionsPage() {
  const navigate = useNavigate();

  return (
    <div className="p-6">
      <div className="mb-6">
        <h1 className="text-3xl font-bold">Live Sessions</h1>
        <p className="text-sm text-slate-500">
          Start or join online tutoring sessions.
        </p>
      </div>

      <div className="grid gap-4">
        {mockSessions.map((session) => (
          <div
            key={session.id}
            className="rounded-2xl border bg-white p-5 shadow-sm"
          >
            <div className="flex items-start justify-between">
              <div>
                <h2 className="text-xl font-semibold">{session.title}</h2>
                <p className="mt-1 text-slate-600">{session.tutorName}</p>
                <p className="text-slate-500">{session.time}</p>
              </div>

              <span
                className={`rounded-full px-3 py-1 text-sm font-medium ${
                  session.status === "LIVE"
                    ? "bg-green-100 text-green-700"
                    : "bg-yellow-100 text-yellow-700"
                }`}
              >
                {session.status}
              </span>
            </div>

            <div className="mt-4">
              <button
                onClick={() => navigate(`/live-sessions/${session.id}`)}
                className="rounded-lg bg-green-600 px-4 py-2 text-white"
              >
                {session.status === "LIVE" ? "Join Session" : "View Session"}
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
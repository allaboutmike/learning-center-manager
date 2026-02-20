import React, { useState, useMemo } from "react";
import type { Session, TabType, Child, SessionData } from "../types/session";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
// import { useNavigate } from "react-router-dom";

export default function ParentProfilePage() {
  const children: Child[] = useMemo(
    () => [
      { id: "1", childName: "Keysha" },
      { id: "2", childName: "Brenda" },
      { id: "3", childName: "Latesha" },
    ],
    [],
  );

  // const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState<TabType>("upcoming");
  const [selectedChildId, setSelectedChildId] = useState<string>("all");

  const url = "/api/sessions";
  const sessionData = useLearningCenterAPI<SessionData[]>(url);

  // Initializing the selected child
  React.useEffect(() => {
    if (children.length > 0 && !selectedChildId) {
      setSelectedChildId(children[0].id);
    }
  }, [children, selectedChildId]);

  {
    /* Retrieving the current sessions details based on the active tab and selected child. If the child has no sessions booked, a message will appear informing the parent that the current selected child has no upcoming sessions or past sessions. Otherwise the session details will show. If the parent hasn't added a child to their account, the dropdown list will be empty. */
  }
  const getCurrentSessions = (): Session[] => {
    if (!sessionData) return [];
    if (selectedChildId === "all") {
      // Object.values gives us: [{ upcoming: [], past: [] }, { upcoming: [], past: [] }]
      // .flatMap extracts the specific tab array and flattens them into one Session[] array
      return Object.values(sessionData).flatMap((childData) => {
        const sessionForTab = childData[activeTab];
        return Array.isArray(sessionForTab) ? sessionForTab : [];
      });
    }
    const childData = sessionData[0];
    if (childData) return [];

    return childData[activeTab] ?? [];
  };

  const currentSession = getCurrentSessions();
  const hasNoSessions = currentSession.length === 0;
  const selectedChild = children.find((child) => child.id === selectedChildId);

  return (
    <div className="parent-session-container">
      {/* Tab Navigation */}
      <div className="tabs">
        <button
          className={activeTab === "upcoming" ? "active" : ""}
          onClick={() => setActiveTab("upcoming")}
        >
          Upcoming Sessions
        </button>

        <button
          className={activeTab === "past" ? "active" : ""}
          onClick={() => setActiveTab("past")}
        >
          Past Sessions
        </button>
      </div>

      {/* Booking button */}
      <div className="booking-btn">
        <button className="">Book A Session</button>
      </div>

      {/* Children Dropdown */}
      <div className="child-selector">
        <label htmlFor="child-select">Select Child:</label>
        <select
          id="child-select"
          value={selectedChildId}
          onChange={(e) => {
            setSelectedChildId(e.target.value);
          }}
        >
          <option value="all">All Children</option>
          {children.map((children) => (
            <option key={children.id} value={children.id}>
              {children.childName}
            </option>
          ))}
        </select>
      </div>

      {/* Sessions Display or Empty State */}
      <div className="session-content">
        {hasNoSessions ? (
          <div className="empty-state">
            <h3>No {activeTab} sessions</h3>
            <p>
              {selectedChildId === "all"
                ? `No ${activeTab} sessions scheduled for any children.`
                : `${selectedChild?.childName} does not have any ${activeTab} sessions ${activeTab === "upcoming" ? "scheduled" : "recorded"}.`}
            </p>
          </div>
        ) : (
          currentSession.map((session) => (
            <div key={session.sessionId} className="session-card">
              <h2>{session.student.childName}</h2>
              <h4>{session.subject}</h4>
              <p>Tutor: {session.tutorId}</p>
              <p>Session Notes: {session.sessionNotes}</p>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

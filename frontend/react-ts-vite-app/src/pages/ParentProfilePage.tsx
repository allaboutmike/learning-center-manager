import React, { useState, useMemo } from "react";
import { useParams } from "react-router-dom";
import type { TabType, SessionData, Child } from "../types/parents";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import type { Session } from "@/types/session";
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { AppSidebar } from "@/components/app-sidebar";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
// import { useNavigate } from "react-router-dom";

export default function ParentProfilePage() {
  // extracting parentId from URL params
  const { parentId } = useParams<{ parentId: string }>();

  // Use child or string as the useState reference?
  const [activeTab, setActiveTab] = useState<TabType>("upcoming");
  const [selectedChildId, setSelectedChildId] = useState<string>("all");

  // Fetching the children from the API
  const children = useLearningCenterAPI<Child[]>(
    parentId ? `/api/parents/${parentId}/children` : "",
  );

  // Fetching the session data (keyed by childId)
  const sessionData = useLearningCenterAPI<
    Record<string, Record<TabType, Session[]>>
  >(parentId ? `/api/parents/${parentId}/sessions/upcoming` : "");

  // Logic to get sessions based on selection
  const getCurrentSessions = (): Session[] => {
    if (!sessionData) return [];

    if (selectedChildId === "all") {
      return Object.values(sessionData).flatMap(
        (childData) => childData[activeTab] ?? [],
      );
    }

    const childData = sessionData[selectedChildId];
    return childData ? (childData[activeTab] ?? []) : [];
  };

  const currentSessions = getCurrentSessions();
  const hasNoSessions = currentSessions.length === 0;

  // Find the selected child object for the empty state message
  const selectedChild = Array.isArray(children)
    ? children.find((c) => c.id.toString() === selectedChildId)
    : undefined;

  return (
    <div>
      <SidebarProvider>
        <AppSidebar variant="inset" />
        <SidebarInset>
          <div className="p-6 flex flex-col w-full gap-6">
            {/* Top Header Row: Title and Button */}
            <div className="flex justify-between items-center w-full">
              <h1 className="text-2xl font-bold">Parent Profile</h1>
              <div className="booking-btn">
                <button className="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors">
                  Book A Session
                </button>
              </div>
            </div>

            <div className="parent-session-container w-full flex flex-col items-center">
              {/* Tab Navigation */}
              <Tabs
                defaultValue="upcoming"
                className="w-full flex flex-col items-center"
              >
                <TabsList
                  variant="line"
                  className="flex justify-center text-white mb-8"
                >
                  <TabsTrigger
                    value="upcoming"
                    className={`text-white data-[state=active]:text-white ${activeTab === "upcoming" ? "active" : ""}`}
                    onClick={() => setActiveTab("upcoming")}
                  >
                    Upcoming
                  </TabsTrigger>
                  <TabsTrigger
                    value="past"
                    className={`text-white data-[state=active]:text-white ${activeTab === "past" ? "active" : ""}`}
                    onClick={() => setActiveTab("past")}
                  >
                    Past
                  </TabsTrigger>
                  <TabsTrigger className="text-white" value="reports">
                    Progress Reports
                  </TabsTrigger>
                </TabsList>

                {/* Dropdown List */}
                <div className="child-selector flex justify-center items-center gap-2 mb-8">
                  <label htmlFor="child-select" className="font-medium">
                    Select Child:
                  </label>
                  <select
                    id="child-select"
                    className="border rounded px-2 py-1 bg-white text-black"
                    value={selectedChildId}
                    onChange={(e) => setSelectedChildId(e.target.value)}
                  >
                    <option value="all">All Children</option>
                    {children?.map((child) => (
                      <option key={child.id} value={child.id}>
                        {child.childName}
                      </option>
                    ))}
                  </select>
                </div>

                {/* Sessions Display */}
                <div className="session-content w-full flex flex-col items-center">
                  {hasNoSessions ? (
                    <div className="empty-state text-center py-10">
                      <h3 className="text-lg font-semibold">
                        No {activeTab} sessions
                      </h3>
                      <p className="text-gray-500">
                        {selectedChildId === "all"
                          ? `No ${activeTab} sessions scheduled for any children.`
                          : `${selectedChild?.childName} does not have any ${activeTab} sessions ${activeTab === "upcoming" ? "scheduled" : "recorded"}.`}
                      </p>
                    </div>
                  ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 w-full max-w-4xl">
                      {currentSessions.map((session) => (
                        <div
                          key={session.sessionId}
                          className="session-card p-4 border rounded-lg shadow-sm"
                        >
                          <h2 className="font-bold text-xl">
                            {session.childId}
                          </h2>
                          <h4 className="text-blue-600 font-medium">
                            {session.subjectId}
                          </h4>
                          <p className="text-sm text-gray-600">
                            Tutor: {session.tutorId}
                          </p>
                          <p className="mt-2 italic text-gray-700">
                            Notes: {session.sessionNotes}
                          </p>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </Tabs>
            </div>
          </div>
        </SidebarInset>
      </SidebarProvider>
    </div>
  );
}

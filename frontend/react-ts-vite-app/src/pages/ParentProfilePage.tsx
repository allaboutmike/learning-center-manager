import { useEffect, useState, useMemo, useCallback } from "react";
import type { TabType, ChildResponse, SessionData, Parent } from "../types/parents";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { AppSidebar } from "@/components/app-sidebar";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import { Button } from "@/components/ui/button"
import BuyCreditsDialog from "./BuyCreditsDialog"
import type { Session } from "../types/session";
// import { useNavigate } from "react-router-dom";
import CreditsDisplay from '../components/CreditsDisplay';

function ChildSessionFetcher({
  parentId,
  childId,
  onData,
}: {
  parentId: number;
  childId: string;
  onData: (
    childId: string,
    data: { upcoming: Session[]; past: Session[] },
  ) => void;
}) {
  const upcoming = useLearningCenterAPI<Session[]>(
    `/api/parents/${parentId}/children/${childId}/sessions/upcoming`,
  );
  const past = useLearningCenterAPI<Session[]>(
    `/api/parents/${parentId}/children/${childId}/sessions/past`,
  );

  useEffect(() => {
    if (upcoming && past) {
      onData(childId, { upcoming, past });
    }
  }, [upcoming, past, childId, onData]);
  return null;
}

export default function ParentProfilePage() {
  // extracting parentId from URL params
  const parentId = 1;

  // Use child or string as the useState reference?
  const [activeTab, setActiveTab] = useState<TabType>("upcoming");
  const [selectedChildId, setSelectedChildId] = useState<string>("all");
  const [allSessions, setAllSessions] = useState<SessionData>({});

  // controls opening and closing the BuyCreditsDialog
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => setIsModalOpen(true);

  // Fetching the children from the API
  const children = useLearningCenterAPI<ChildResponse[]>(
    `/api/parents/${parentId}/children`,
  );

  const parent = useLearningCenterAPI<Parent>(
    `/api/parents/${parentId}`,
  );

  
  const handleSessionData = useCallback(
    (childId: string, data: { upcoming: Session[]; past: Session[] }) => {
      setAllSessions((prev) => ({ ...prev, [childId]: data }));
    },
    [],
  );

  // Logic to get sessions based on childId selection. Replaced the session hook with memoized logic to handle both "all" and specific child selection.
  const currentSessions = useMemo(() => {
    if (selectedChildId === "all") {
      // return [1,2,3,4,5,6].map(item => item);
      return Object.values(allSessions).flatMap(
        (data) => data[activeTab] || [],
      );
    }
    return allSessions[selectedChildId]?.[activeTab] || [];
  }, [allSessions, selectedChildId, activeTab]);

  const hasNoSessions = currentSessions.length === 0;

  // Find the selected child object for the empty state message
  const selectedChild = Array.isArray(children)
    ? children.find((c) => c.childId.toString() === selectedChildId)
    : undefined;

  return (
    <div>
      <SidebarProvider>
        <AppSidebar variant="inset" />
        <SidebarInset>
          <div className="p-6 flex flex-col w-full gap-6">
            {Array.isArray(children) &&
              children.map((child) => (
                <ChildSessionFetcher
                  key={child.childId}
                  parentId={parentId}
                  childId={child.childId.toString()}
                  onData={handleSessionData}
                />
              ))}
            {/* Top Header Row: Title and Button */}
            <div className="flex justify-between items-center w-full">
              <h1 className="text-2xl font-bold">Parent Profile</h1>
              <div className="flex items-center gap-4">
                <CreditsDisplay openModal={openModal} />

                <Button
                  className="px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition-colors"
                  disabled={!parent || parent.credits === 0}
                  onClick={() => (      window.location.href = "/tutors"
                  )}
                >
                  Book A Session
                </Button>
              </div>
            </div>

            <div className="parent-session-container w-full flex flex-col items-center">
              <Tabs
                value={activeTab}
                onValueChange={(v) => setActiveTab(v as TabType)}
                className="w-full flex flex-col items-center"
              >
                <TabsList
                  variant="line"
                  className="flex justify-center text-white mb-8"
                >
                  <TabsTrigger
                    value="upcoming"
                    className="text-white data-[state=active]:text-white"
                  >
                    Upcoming
                  </TabsTrigger>
                  <TabsTrigger
                    value="past"
                    className="text-white data-[state=active]:text-white"
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
                    {Array.isArray(children) &&
                      children.map((child) => (
                        <option key={child.childId} value={child.childId}>
                          {child.firstName} (Grade {child.gradeLevel})
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
                          ? `Select a specific child to view ${activeTab} sessions.`
                          : `${selectedChild?.firstName} does not have any ${activeTab} sessions ${activeTab === "upcoming" ? "scheduled" : "recorded"}.`}
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
                            Session #{session.sessionId}
                          </h2>
                          <h4 className="text-blue-600 font-medium">
                            Subject ID: {session.subjectId}
                          </h4>
                          <p className="text-sm text-gray-600">
                            Tutor ID: {session.tutorId}
                          </p>
                          <p className="text-sm text-gray-500">
                            Date: {new Date(session.date).toLocaleDateString()}
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
      <BuyCreditsDialog parentId={parentId} open={isModalOpen} onOpenChange={(open) => setIsModalOpen(open)} />
    </div>
  );
}

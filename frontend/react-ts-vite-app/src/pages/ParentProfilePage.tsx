// src/pages/ParentProfilePage.tsx

import { useEffect, useState, useMemo, useCallback } from "react";
import type { ChildResponse, SessionData, Parent } from "../types/parents";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar";
import { Button } from "@/components/ui/button";
import { usePersona } from "@/context/usePersona";
import BuyCreditsDialog from "./BuyCreditsDialog";
import type { Session } from "../types/session";
import { Navigate, useNavigate } from "react-router-dom";
import CreditsDisplay from "../components/CreditsDisplay";
import RegisterChildModal from "../components/RegisterChildModal";
import { format, parseISO } from "date-fns";

type ParentTab = "upcoming" | "past" | "reports";
type SessionTab = "upcoming" | "past";

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
  const navigate = useNavigate();
  const { persona } = usePersona();

  if (persona.role !== "parent" || persona.id === null) {
    return <Navigate to="/persona" replace />;
  }

  const parentId = persona.id as number;

  // controls opening and closing the BuyCreditsDialog
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => setIsModalOpen(true);
  const [isRegisterChildOpen, setIsRegisterChildOpen] = useState(false);
  const [successBanner, setSuccessBanner] = useState<string | null>(null);

  // Fetching the children from the API
  const [activeTab, setActiveTab] = useState<ParentTab>("upcoming");
  const [selectedChildId, setSelectedChildId] = useState<string>("all");
  const [allSessions, setAllSessions] = useState<SessionData>({});

  const children = useLearningCenterAPI<ChildResponse[]>(
    `/api/parents/${parentId}/children`,
  );

  const parent = useLearningCenterAPI<Parent>(`/api/parents/${parentId}`);

  const handleSessionData = useCallback(
    (childId: string, data: { upcoming: Session[]; past: Session[] }) => {
      setAllSessions((prev) => ({ ...prev, [childId]: data }));
    },
    [],
  );

  const currentSessions = useMemo(() => {
    if (activeTab === "reports") return [];

    const sessionTab: SessionTab = activeTab;

    if (selectedChildId === "all") {
      return Object.values(allSessions).flatMap(
        (data) => data[sessionTab] || [],
      );
    }

    return allSessions[selectedChildId]?.[sessionTab] || [];
  }, [allSessions, selectedChildId, activeTab]);

  const hasNoSessions = currentSessions.length === 0;

  const selectedChild =
    Array.isArray(children) && selectedChildId !== "all"
      ? children.find((c) => c.childId.toString() === selectedChildId)
      : undefined;
  const handleBookSession = () => {
    if (!parent || parent.credits === 0) {
      openModal();
      return;
    }

    navigate("/tutors");
  };

  // Refactor the view for accessibility options
  return (
    <div>
      <SidebarProvider>
        <SidebarInset>
          <div className="p-8 flex flex-col w-full gap-8">

  {/* FETCH SESSION DATA */}
  {Array.isArray(children) &&
    children.map((child) => (
      <ChildSessionFetcher
        key={child.childId}
        parentId={parentId}
        childId={child.childId.toString()}
        onData={handleSessionData}
      />
    ))}

  {/* HEADER */}
  <div className="flex justify-between items-center">
    <div>
      <h1 className="text-3xl font-bold text-slate-800">
        Welcome, {parent?.name}
      </h1>
      <p className="text-slate-500 text-sm">
        Manage your children's tutoring sessions
      </p>
    </div>

    <div className="flex items-center gap-4">
      <Button
        className="bg-green-600 hover:bg-green-700 text-white px-6"
        onClick={handleBookSession}
      >
        Book Session
      </Button>

      <CreditsDisplay openModal={openModal} parentId={parentId} />
    </div>
  </div>

  {/* DASHBOARD CARDS */}
  <div className="grid grid-cols-1 md:grid-cols-3 gap-4">

    <div className="bg-white rounded-xl shadow-sm p-4 border">
      <h3 className="text-sm text-gray-500">Children</h3>
      <p className="text-2xl font-bold">
        {children?.length ?? 0}
      </p>
    </div>

    <div className="bg-white rounded-xl shadow-sm p-4 border">
      <h3 className="text-sm text-gray-500">Credits</h3>
      <p className="text-2xl font-bold">
        {parent?.credits ?? 0}
      </p>
    </div>

    <div className="bg-white rounded-xl shadow-sm p-4 border">
      <h3 className="text-sm text-gray-500">Upcoming Sessions</h3>
      <p className="text-2xl font-bold">
        {Object.values(allSessions).flatMap(s => s.upcoming).length}
      </p>
    </div>

  </div>

  {/* SUCCESS BANNER */}
  {successBanner && (
    <div className="rounded-md border border-green-200 bg-green-50 px-4 py-3 text-green-800">
      <p className="font-medium">{successBanner}</p>
      <p className="text-sm">You can now book tutoring sessions.</p>
    </div>
  )}

  {/* SESSIONS PANEL */}
  <div className="bg-white rounded-xl shadow-sm border p-6">

    <Tabs
      value={activeTab}
      onValueChange={(v) => setActiveTab(v as ParentTab)}
      className="w-full"
    >
      <div className="flex justify-between items-center mb-6">

        <TabsList variant="line">
          <TabsTrigger value="upcoming">
            Upcoming
          </TabsTrigger>

          <TabsTrigger value="past">
            Past
          </TabsTrigger>
        </TabsList>

        {/* CHILD FILTER */}
        <select
          className="border rounded-md px-3 py-2 text-sm"
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

      {/* SESSION LIST */}
      {hasNoSessions ? (
        <div className="text-center py-12">
          <h3 className="text-lg font-semibold">
            No {activeTab} sessions
          </h3>
          <p className="text-gray-500">
            {selectedChildId === "all"
              ? `Select a specific child to view ${activeTab} sessions.`
              : `${selectedChild?.firstName} does not have any ${activeTab} sessions.`}
          </p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {currentSessions.map((session) => (
            <div
              key={session.sessionId}
              className="p-4 border rounded-xl bg-white shadow-sm hover:shadow-md transition"
            >
              <h2 className="font-bold text-lg">
                {session.childName}
              </h2>

              <p className="text-green-600 font-medium">
                {Array.isArray(session.subjectName)
                  ? session.subjectName
                      .map((s) =>
                        typeof s === "string" ? s : s.name,
                      )
                      .join(", ")
                  : typeof session.subjectName === "string"
                  ? session.subjectName
                  : session.subjectName.name}
              </p>

              <p className="text-sm text-gray-600">
                Tutor: {session.tutorName}
              </p>

              <p className="text-sm text-gray-500">
                {format(parseISO(session.time), "MMM d, yyyy h:mm a")}
              </p>
            </div>
          ))}
        </div>
      )}

    </Tabs>
  </div>
</div>
        </SidebarInset>
      </SidebarProvider>
      <BuyCreditsDialog
        parentId={parentId}
        open={isModalOpen}
        onOpenChange={(open) => setIsModalOpen(open)}
      />
      <RegisterChildModal
        open={isRegisterChildOpen}
        onOpenChange={setIsRegisterChildOpen}
        parentId={parentId}
        onChildCreated={(child) => {
          setSuccessBanner(`${child.firstName} was successfully registered.`);
        }}
      />
    </div>
  );
}

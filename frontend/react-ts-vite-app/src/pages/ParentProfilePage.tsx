// src/pages/ParentProfilePage.tsx

import { useEffect, useState, useMemo, useCallback } from "react"
import type { ChildResponse, SessionData, Parent } from "../types/parents"
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI"
import { Tabs, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { AppSidebar } from "@/components/app-sidebar"
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar"
import { Button } from "@/components/ui/button"
import BuyCreditsDialog from "./BuyCreditsDialog"
import type { Session } from "../types/session"
import CreditsDisplay from "../components/CreditsDisplay"
import ChildProgressDashboardPage from "./ChildProgressDashboardPage"

type ParentTab = "upcoming" | "past" | "reports"
type SessionTab = "upcoming" | "past"

function ChildSessionFetcher({
  parentId,
  childId,
  onData,
}: {
  parentId: number
  childId: string
  onData: (childId: string, data: { upcoming: Session[]; past: Session[] }) => void
}) {
  const upcoming = useLearningCenterAPI<Session[]>(
    `/api/parents/${parentId}/children/${childId}/sessions/upcoming`,
  )

  const past = useLearningCenterAPI<Session[]>(
    `/api/parents/${parentId}/children/${childId}/sessions/past`,
  )

  useEffect(() => {
    if (upcoming && past) {
      onData(childId, { upcoming, past })
    }
  }, [upcoming, past, childId, onData])

  return null
}

export default function ParentProfilePage() {
  const parentId = 1

  

  // controls opening and closing the BuyCreditsDialog
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => setIsModalOpen(true);

  // Fetching the children from the API
  const [activeTab, setActiveTab] = useState<ParentTab>("upcoming")
  const [selectedChildId, setSelectedChildId] = useState<string>("all")
  const [allSessions, setAllSessions] = useState<SessionData>({})

  const children = useLearningCenterAPI<ChildResponse[]>(
    `/api/parents/${parentId}/children`,
  )

  const parent = useLearningCenterAPI<Parent>(
    `/api/parents/${parentId}`,
  );

  
  const handleSessionData = useCallback(
    (childId: string, data: { upcoming: Session[]; past: Session[] }) => {
      setAllSessions((prev) => ({ ...prev, [childId]: data }))
    },
    [],
  )

  const currentSessions = useMemo(() => {
    if (activeTab === "reports") return []

    const sessionTab: SessionTab = activeTab

    if (selectedChildId === "all") {
      return Object.values(allSessions).flatMap((data) => data[sessionTab] || [])
    }

    return allSessions[selectedChildId]?.[sessionTab] || []
  }, [allSessions, selectedChildId, activeTab])

  const hasNoSessions = currentSessions.length === 0

  const selectedChild =
    Array.isArray(children) && selectedChildId !== "all"
      ? children.find((c) => c.childId.toString() === selectedChildId)
      : undefined

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

            <div className="w-full flex flex-col items-center">
              <Tabs
                value={activeTab}
                onValueChange={(v) => setActiveTab(v as ParentTab)}
                className="w-full flex flex-col items-center"
              >
                <TabsList variant="line" className="flex justify-center mb-8">
                  <TabsTrigger value="upcoming">Upcoming</TabsTrigger>
                  <TabsTrigger value="past">Past</TabsTrigger>
                  <TabsTrigger value="reports">Progress Reports</TabsTrigger>
                </TabsList>

                <div className="flex justify-center items-center gap-2 mb-8">
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



                {activeTab === "reports" && (
                  <div className="w-full flex flex-col items-center">
                    {selectedChildId === "all" ? (
                      <div className="text-center py-10">
                        <h3 className="text-lg font-semibold">Select a child</h3>
                        <p className="text-gray-500">
                          Progress reports are shown per child. Please select a specific child.
                        </p>
                      </div>
                    ) : (
                      <div className="w-full">
                        <ChildProgressDashboardPage
                          parentId={parentId}
                          childId={Number(selectedChildId)}
                        />
                      </div>
                    )}
                  </div>
                )}

                {activeTab !== "reports" && (
                  <div className="w-full flex flex-col items-center">
                    {hasNoSessions ? (
                      <div className="text-center py-10">
                        <h3 className="text-lg font-semibold">No {activeTab} sessions</h3>
                        <p className="text-gray-500">
                          {selectedChildId === "all"
                            ? `Select a specific child to view ${activeTab} sessions.`
                            : `${selectedChild?.firstName ?? "This child"} does not have any ${activeTab} sessions.`}
                        </p>
                      </div>
                    ) : (
                      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 w-full max-w-4xl">
                        {currentSessions.map((session) => (
                          <div
                            key={session.sessionId}
                            className="p-4 border rounded-lg shadow-sm"
                          >
                            <h2 className="font-bold text-xl">
                              Session #{session.sessionId}
                            </h2>
                            <p className="text-sm text-gray-500">
                              Date: {new Date(session.date).toLocaleDateString()}
                            </p>
                          </div>
                        ))}
                      </div>
                    )}
                  </div>
                )}
              </Tabs>
            </div>
          </div>
        </SidebarInset>
      </SidebarProvider>
      <BuyCreditsDialog parentId={parentId} open={isModalOpen} onOpenChange={(open) => setIsModalOpen(open)} />
    </div>
  )
}
import { Button } from "@/components/ui/button";
import { useSearchParams } from "react-router-dom";
import type { Session } from "../types/session";
import { useLearningCenterAPI } from "../hooks/useLearningCenterAPI";

export default function ConfirmationPage() {
    const [searchParams] = useSearchParams();
    const sessionId = searchParams.get("sessionId");

    if (!sessionId) return <p>Missing sessionId</p>;

    const data = useLearningCenterAPI<Session>(
        sessionId ? `/api/sessions/${sessionId}` : ""
    );

    if (!data) return null;
    return (
        <div className="h-200 flex flex-col justify-center items-center text-black">
            <div>
                <h1>Confirmed ✅</h1>
                <p>
                    {data?.childName
                        ? `${data.childName}'s session with ${data.tutorName ?? "your tutor"
                        } has been confirmed for ${data.time
                            ? new Date(data.time).toLocaleString()
                            : "the selected time"
                        }.`
                        : "Loading confirmation details..."}
                </p>
                {/* {data && (
                    <>
                        <p>Child ID: {data.childId}</p>
                        <p>Subject ID: {data.subjectId}</p>
                        <p>Tutor ID: {data.tutorId}</p>

                        <p>
                            Time: {data.time
                                ? new Date(data.time).toLocaleDateString("en-US", {
                                    weekday: "long",
                                    month: "long",
                                    day: "numeric",
                                    year: "numeric",
                                }) + " at " +
                                new Date(data.time).toLocaleTimeString("en-US", {
                                    hour: "numeric",
                                    minute: "2-digit",
                                    hour12: true,
                                })
                                : "Not available"}
                        </p>
                    </>
                )} */}
            </div>
            <Button className="mt-10 mr-10" variant="secondary" onClick={() => window.location.href = "/"}>Return to Search Tutors</Button>

        </div>
    );
}

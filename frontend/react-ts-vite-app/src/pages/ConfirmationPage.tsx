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
            </div>
            <Button className="mt-10 mr-10" variant="secondary" onClick={() => window.location.href = "/"}>Return to Search Tutors</Button>

        </div>
    );
}

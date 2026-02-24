import { Button } from "@/components/ui/button";
import { useLocation } from "react-router-dom";

type ConfirmationState = {
    tutorName: string;
};

export default function ConfirmationPage() {
    const location = useLocation();
    const state = location.state as ConfirmationState | null;

    return (
        <div className="h-200 flex flex-col justify-center items-center text-black">
            <div>
                <h1>Confirmed ✅</h1>
                <p style={{ marginTop: 12, fontSize: 18 }}>
                    {state?.tutorName
                        ? `You booked a session with ${state.tutorName}.`
                        : "Your session has been confirmed."}
                </p>
             
            </div>
            <Button className="mt-10 mr-10" variant="secondary" onClick={() => window.location.href = "/"}>Return to Search Tutors</Button>
            
        </div>
    );
}

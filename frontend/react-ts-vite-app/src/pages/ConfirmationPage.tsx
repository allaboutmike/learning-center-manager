import { useLocation } from "react-router-dom";

type ConfirmationState = {
    tutorName: string;
};

export default function ConfirmationPage() {
    const location = useLocation();
    const state = location.state as ConfirmationState | null;

    return (
        <div
            style={{
                height: "100vh",
                width: "100vw",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                textAlign: "center",
                backgroundColor: "white",
                color: "black",
            }}
        >
            <div>
                <h1>Confirmed ✅</h1>
                <p style={{ marginTop: 12, fontSize: 18 }}>
                    {state?.tutorName
                        ? `You booked a session with ${state.tutorName}.`
                        : "Your session has been confirmed."}
                </p>
            </div>
        </div>
    );
}

import { Button } from "@/components/ui/button"
import type { Tutor } from "../types/tutor"
import type { TutorTimeslot } from "../types/tutorTimeslot"



type Props = {
  tutor: Tutor
  slot: TutorTimeslot
  onClose: () => void
}

export default function SessionReviewModal({
  tutor,
  slot,
  onClose,
}: Props) {

  const sessionDate = new Date(slot.start)

  const formattedDate = sessionDate.toLocaleDateString("en-US", {
    weekday: "long",
    month: "long",
    day: "numeric",
    year: "numeric",
  })

  const formattedTime = sessionDate.toLocaleTimeString("en-US", {
    hour: "numeric",
    minute: "2-digit",
    hour12: true,
  })

  const handleConfirmSession = async () => {
    try {
         // Creates the session via POST request with all required IDs
      await fetch("/api/sessions", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          tutorId: tutor.tutorId,
          tutorTimeSlotId: slot.tutorTimeslotId,
        }),
      })

      onClose()
      window.location.href = "/confirmation"
    } catch (error) {
      console.error("Failed to create session:", error)
      alert("Failed to create session. Please try again.")
    }
  };
  if (!tutor || !slot) return <p>Loading...</p>;
  
  return (
    <div className="space-y-6">
      <h2 className="text-xl font-semibold">
        Confirm Your Session
      </h2>

      <div className="space-y-2">
        <h3>
            You've booked a session with {tutor.name} on {formattedDate} at {formattedTime}.
        </h3>
            <p>Choose your Subject: {tutor.name}</p>
            <p>Click the button below to confirm your session.</p>
      </div>
    <div className="flex gap-4">
        <Button variant="secondary" onClick={handleConfirmSession}>
          Confirm Session
        </Button>

        <Button variant="secondary" onClick={onClose}>
          Cancel
        </Button>
</div>
      </div>
  )
}
import { Button } from "@/components/ui/button"
import type { Tutor } from "../types/tutor"
import type { TutorTimeslot } from "../types/tutorTimeslot"
import { useLearningCenterPost } from "../hooks/useLearningCenterAPI";



type Props = {
  tutor: Tutor
  slot: TutorTimeslot
  subjectId: number | null
  childId: number | null
  onClose: () => void
}

export default function SessionReviewModal({
  tutor,
  slot,
  subjectId,
  childId,
  onClose,
}: Props) {
  const post = useLearningCenterPost();
  const handleConfirmSession = async () => {
    if (!childId || !subjectId) return alert("Please select a child and subject first.");

    // Using var for the payload as requested
    var sessionData = {
      tutorId: tutor.tutorId,
      childId: childId,
      sessionNotes: "",
      tutorTimeSlotId: slot.tutorTimeslotId,
      subjectId: subjectId
    };

    try {
      const createdSession = await post<{ sessionId: number }>(
        "/api/sessions",
        sessionData
      );

      window.location.href = `/confirmation?sessionId=${createdSession.sessionId}`;
    } catch (error) {
      console.error("Error creating session:", error);
      alert("Save failed. Please try again.");
    }
  };
  const sessionDate = new Date(slot.start)
  const selectedSubject = tutor.subjects.find((s) => s.subjectId === subjectId);

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



  if (!tutor || !slot) return <p>Loading...</p>;

  return (
    <div className="space-y-6">
      <h2 className="text-xl font-semibold">
        Confirm Your Session
      </h2>

      <div className="space-y-2">
        <h3>
          You're about to book a session with {tutor.name} on {formattedDate} at {formattedTime}.        </h3>
        <p>Subject: {selectedSubject?.name ?? "Not selected"}</p>
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